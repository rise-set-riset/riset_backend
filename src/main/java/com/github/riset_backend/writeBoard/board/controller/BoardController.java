package com.github.riset_backend.writeBoard.board.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.writeBoard.board.dto.BoardRequestDto;
import com.github.riset_backend.writeBoard.board.dto.BoardResponseDto;
import com.github.riset_backend.writeBoard.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public ResponseEntity<List<BoardResponseDto>> getAllBoard(@RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "1") Long empolyeeNo
//                                                              @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
//        List<BoardResponseDto> boards = boardService.getAllBoard(customUserDetails.getEmployee(), page, size, empolyeeNo);
        List<BoardResponseDto> boards = boardService.getAllBoard(page, size, empolyeeNo);

        return ResponseEntity.ok(boards);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardResponseDto>> getSearchAllBoard(@RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "") String title) {
        List<BoardResponseDto> boards = boardService.getSearchAllBoard(page, size, title);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<BoardResponseDto> getBoardByBoardNo(@PathVariable Long boardNo) {
        BoardResponseDto board = boardService.getBoardByBoardNo(boardNo);
        return ResponseEntity.ok(board);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardResponseDto> createBoard(@RequestPart(value = "dto", required = false) BoardRequestDto boardRequestDto,
                                                        @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles
//                                                        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
//        BoardResponseDto board = boardService.createBoard(boardRequestDto, customUserDetails.getEmployee().getEmployeeNo() , multipartFiles);
        BoardResponseDto board = boardService.createBoard(boardRequestDto,1L, multipartFiles);

        return ResponseEntity.ok(board);
    }


    @PatchMapping("/{boardNo}")
    public ResponseEntity<BoardResponseDto> update(@RequestPart(required = false) BoardRequestDto boardRequestDto,
                                                   @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
                                                   @PathVariable Long boardNo) {
        BoardResponseDto board = boardService.updateBoard(boardRequestDto, multipartFiles, boardNo);
        return ResponseEntity.ok(board);
    }

    @PatchMapping("deleted/{boardNo}")
    public ResponseEntity<BoardResponseDto> deleteBoard(@PathVariable Long boardNo) {
        BoardResponseDto board = boardService.deletedBoard(boardNo);
        return ResponseEntity.ok(board);
    }
}
