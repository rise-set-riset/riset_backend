package com.github.riset_backend.writeBoard.board.controller;

import com.github.riset_backend.writeBoard.board.dto.BoardRequestDto;
import com.github.riset_backend.writeBoard.board.dto.BoardResponseDto;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public ResponseEntity<List<BoardResponseDto>> getAllBoard (@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "") String title) {
        List<BoardResponseDto> boards = boardService.getAllBoard(page, size);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardResponseDto>> getSearchAllBoard (@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "") String title) {
        List<BoardResponseDto> boards = boardService.getSearchAllBoard(page, size, title);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<BoardResponseDto> getBoardByBoardNo (@PathVariable Long boardNo) {
        BoardResponseDto board = boardService.getBoardByBoardNo(boardNo);
        return ResponseEntity.ok(board);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardResponseDto> createBoard (@RequestPart(value = "dto", required = false) BoardRequestDto boardRequestDto,
                                                         @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
                                                         HttpServletRequest request) {

        log.info("boardRequestDto = {}", boardRequestDto);
        log.info("multipartFiles = {}", multipartFiles);

//        String token = request.getHeader("AUTHORAZATION");
        BoardResponseDto board = boardService.createBoard(boardRequestDto, "token", multipartFiles);
        return ResponseEntity.ok(board);
    }


    @PatchMapping("/{boardNo}")
    public ResponseEntity<BoardResponseDto> update (@RequestPart(required = false) BoardRequestDto boardRequestDto,
                                                    @RequestPart(value = "file", required=false) List<MultipartFile> multipartFiles,
                                                    @PathVariable Long boardNo) {
        BoardResponseDto board = boardService.updateBoard(boardRequestDto, multipartFiles, boardNo);
        return ResponseEntity.ok(board);
    }

    @PatchMapping("deleted/{boardNo}")
    public  ResponseEntity<BoardResponseDto> deleteBoard(@PathVariable Long boardNo) {
        BoardResponseDto board = boardService.deletedBoard(boardNo);
        return ResponseEntity.ok(board);
    }

    @PostMapping("/test1")
    public ResponseEntity<?> createBoard1 (@RequestPart(value = "file", required=false) List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(multipartFiles);
    }

    @PostMapping("/test2")
    public ResponseEntity<?> createBoard2 (@RequestPart(value = "dto", required=false) BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardRequestDto);
    }

}
