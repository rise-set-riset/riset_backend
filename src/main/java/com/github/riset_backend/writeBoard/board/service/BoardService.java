package com.github.riset_backend.writeBoard.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.riset_backend.file.entity.File;
import com.github.riset_backend.file.repository.FileRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.writeBoard.board.dto.BoardRequestDto;
import com.github.riset_backend.writeBoard.board.dto.BoardResponseDto;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.board.repository.BoardRepository;
import com.github.riset_backend.writeBoard.boardFile.entity.BoardFile;
import com.github.riset_backend.writeBoard.boardFile.repository.BoardFileRepository;
import com.github.riset_backend.writeBoard.favorite.entity.Favorite;
import com.github.riset_backend.writeBoard.favorite.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final BoardRepository boardRepository;
    private final EmployeeRepository employeeRepository;
    private final FileRepository fileRepository;
    private final BoardFileRepository boardFileRepository;
    private final FavoriteRepository favoriteRepository;
//    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<BoardResponseDto> getAllBoard(Employee employee, int page, int size, String title) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<Board> boards = boardRepository.findSliceByDeletedAndTitleContainingOrderByCreateAtDesc(null ,title, pageRequest);
        List<Long> favoriteBoard = favoriteRepository.findAllByEmployee(employee).stream().map(Favorite::getBoard).map(Board::getBoardNo).toList();

        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();

        boards.forEach(board -> {
            boolean like = !favoriteBoard.isEmpty() && favoriteBoard.contains(board.getBoardNo());
            boardResponseDtos.add(new BoardResponseDto(board, like));
        });

        return boardResponseDtos;
    }

//    @Transactional
//    public List<BoardResponseDto> getSearchAllBoard(int page, int size, String title) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Slice<Board> boards = boardRepository.findSliceByDeletedAndTitleContainingOrderByCreateAtDesc(null ,title, pageRequest);
//        return boards.getContent().stream().map(BoardResponseDto::new).collect(Collectors.toList());
//    }

    @Transactional
    public BoardResponseDto getBoardByBoardNo(Long boardNo) {
        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );

        return new BoardResponseDto(board);
    }


    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto,  Employee employee, List<MultipartFile> multipartFiles) {
        Board board = Board.boardRequestToBoard(boardRequestDto, employee);

        List<File> files = new ArrayList<>();
        List<BoardFile> boardFiles = new ArrayList<>();

        if(multipartFiles != null) {
            multipartFiles.forEach(
                    multipartFile -> {
                        Result result = getResult(multipartFile, board);
                        files.add(result.file());
                        boardFiles.add(result.boardFile());
                    }
            );
        }

        Board newBoard = boardRepository.save(board);
        List<File> newFiles = fileRepository.saveAll(files);
        boardFileRepository.saveAll(boardFiles);

        log.info("newBoard = {}", newBoard);
        log.info("newFiles = {}", newFiles);

        return new BoardResponseDto(newBoard, newFiles);
    }





    private Result getResult(MultipartFile multipartFile, Board board) {
        String fileName = multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FAIL_FILE_UPLOAD);
        }

        log.info("multipartFile.getContentType() = {}", multipartFile.getContentType());

        File file = new File(fileName, amazonS3.getUrl(bucket, fileName).toString(), multipartFile.getSize(), multipartFile.getContentType());
        BoardFile boardFile = new BoardFile(board, file);
        Result result = new Result(file, boardFile);
        return result;
    }

    @Transactional
    public BoardResponseDto updateBoard(BoardRequestDto boardRequestDto, List<MultipartFile> multipartFiles, Long boardNo) {
        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );

        if(boardRequestDto != null) {
//            if(boardRequestDto.getTitle() != null) {
//                board.setTitle(boardRequestDto.getTitle());
//            }
//
//            if(boardRequestDto.getContent() != null) {
//                board.setContent(boardRequestDto.getContent());
//            }
            board.updateBoard(boardRequestDto);
        }

        if(multipartFiles != null) {
            List<File> notDeleteFile = new ArrayList<>();
            List<File> updateFiles = new ArrayList<>();
            List<BoardFile> updateBoardFiles = new ArrayList<>();

            List<File> files = board.getBoardFiles().stream().map(BoardFile::getFile).toList();
            List<String> updateFileNames = multipartFiles.stream().map(MultipartFile::getOriginalFilename).toList();
            for (File file : files) {
                if (!updateFileNames.stream().anyMatch(Predicate.isEqual(file.getFileName()))) {
                    fileRepository.delete(file);
                    boardFileRepository.deleteByFile(file);
                } else {
                    notDeleteFile.add(file);
                }
            }

            for (MultipartFile multipartFile : multipartFiles) {
                if(!notDeleteFile.stream().anyMatch(Predicate.isEqual(multipartFile))) {
                    Result result = getResult(multipartFile, board);
                    updateFiles.add(result.file());
                    updateBoardFiles.add(result.boardFile());
                }
            }

            fileRepository.saveAll(updateFiles);
            boardFileRepository.saveAll(updateBoardFiles);

            board.setBoardFiles(updateBoardFiles);
        }


        return new BoardResponseDto(board);
    }


    @Transactional
    public BoardResponseDto deletedBoard(Long boardNo) {
        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );

        board.setDeleted("y");
        return new BoardResponseDto(board);
    }

    @Transactional
    public List<BoardResponseDto> getAllBoardByEmployeeNo(Employee employee, int page, int size, String title) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<Board> boards = boardRepository.findSliceByEmployeeAndTitleContainingAndDeletedOrderByCreateAt(employee, title, null,pageRequest);

        return boards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }



    private record Result(File file, BoardFile boardFile) {
    }
}
