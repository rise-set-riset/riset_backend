package com.github.riset_backend.writeBoard.boardFile.repository;

import com.github.riset_backend.file.entity.File;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.boardFile.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    Optional<List<BoardFile>> findAllByBoard(Board board);

    Optional<BoardFile> deleteByFile(File file);
}
