package com.github.riset_backend.writeBoard.reply.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.board.repository.BoardRepository;
import com.github.riset_backend.writeBoard.reply.dto.ReplyRequestDto;
import com.github.riset_backend.writeBoard.reply.dto.ReplyResponseDto;
import com.github.riset_backend.writeBoard.reply.entity.Reply;
import com.github.riset_backend.writeBoard.reply.repository.ReplyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final EmployeeRepository employeeRepository;
    private final BoardRepository boardRepository;

    public List<ReplyResponseDto> getReplies(Long boardNo) {
        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );
        List<Reply> replies = replyRepository.findByDeletedAndBoard(null ,board).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REPLY)
        );
        return replies.stream().map(ReplyResponseDto::new).toList();
    }

    public ReplyResponseDto createReply(Long boardNo, ReplyRequestDto replyRequestDto, Employee employee) {
        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );
        Reply reply = new Reply(board, employee, replyRequestDto.getContent());
        Reply newReply = replyRepository.save(reply);

        return new ReplyResponseDto(newReply);
    }

    public ReplyResponseDto updateReply(Long replyNo,ReplyRequestDto replyRequestDto) {
        Reply reply = replyRepository.findByDeletedAndReplyNo(null, replyNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REPLY)
        );

        reply.setContent(replyRequestDto.getContent());
        replyRepository.save(reply);
        return new ReplyResponseDto(reply);
    }

    public ReplyResponseDto deletedReply(Long replyNo) {
        Reply reply = replyRepository.findByDeletedAndReplyNo(null ,replyNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REPLY)
        );
        reply.setDeleted("y");
        replyRepository.save(reply);
        return new ReplyResponseDto(reply);
    }


}
