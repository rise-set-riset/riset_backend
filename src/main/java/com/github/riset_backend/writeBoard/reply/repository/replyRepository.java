package com.github.riset_backend.writeBoard.reply.repository;

import com.github.riset_backend.writeBoard.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface replyRepository extends JpaRepository<Reply, Long> {
}
