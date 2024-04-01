package com.github.riset_backend.writeBoard.board.repository;

import com.github.riset_backend.writeBoard.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
