package com.github.riset_backend.writeBoard.reply.repository;

import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<List<Reply>> findByDeletedAndBoard(String deleted, Board board);

    Optional<Reply> findByDeletedAndReplyNo(String deleted, Long replyNo);
}
