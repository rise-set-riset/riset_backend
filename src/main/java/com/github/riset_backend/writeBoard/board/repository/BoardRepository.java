package com.github.riset_backend.writeBoard.board.repository;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardNo(Long boardNo);

    Slice<Board> findSliceByDeletedOrderByCreateAtDesc(String deleted ,Pageable pageable);

    Slice<Board> findSliceByDeletedAndTitleContainingOrderByCreateAtDesc(String deleted, String title ,Pageable pageable);

    Slice<Board> findSliceByEmployeeAndTitleContainingAndDeletedOrderByCreateAt(Employee employee, String title, String deleted,Pageable pageable);

//    Slice<Board> findSliceByEmployeeAndDeletedAndTitleContainingOrderByCreateAtDesc();
}
