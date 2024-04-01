package com.github.riset_backend.writeBoard.boardFile.repository;

import com.github.riset_backend.writeBoard.boardFile.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
