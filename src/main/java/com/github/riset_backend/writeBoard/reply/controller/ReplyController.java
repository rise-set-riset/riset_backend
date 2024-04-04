package com.github.riset_backend.writeBoard.reply.controller;

import com.github.riset_backend.writeBoard.reply.dto.ReplyRequestDto;
import com.github.riset_backend.writeBoard.reply.dto.ReplyResponseDto;
import com.github.riset_backend.writeBoard.reply.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{boardNo}")
    public ResponseEntity<List<ReplyResponseDto>> getReplies (@PathVariable Long boardNo) {
        List<ReplyResponseDto> replies = replyService.getReplies(boardNo);
        return ResponseEntity.ok(replies);
    }

    @PostMapping("/{boardNo}")
    public ResponseEntity<ReplyResponseDto> createReply (@PathVariable Long boardNo,
                                                         @RequestBody ReplyRequestDto replyRequestDto,
                                                         HttpServletRequest request) {
        ReplyResponseDto reply = replyService.createReply(boardNo, replyRequestDto, request);
        return ResponseEntity.ok(reply);
    }

    @PatchMapping("/update/{replyNo}")
    public ResponseEntity<ReplyResponseDto> updateReply (@PathVariable Long replyNo,  @RequestBody ReplyRequestDto replyRequestDto) {
        ReplyResponseDto reply = replyService.updateReply(replyNo, replyRequestDto);
        return ResponseEntity.ok(reply);
    }

    @PatchMapping("/deleted/{replyNo}")
    public ResponseEntity<ReplyResponseDto> deletedReply (@PathVariable Long replyNo) {
       ReplyResponseDto reply =  replyService.deletedReply(replyNo);
       return ResponseEntity.ok(reply);
    }
}
