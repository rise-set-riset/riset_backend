package com.github.riset_backend.writeBoard.favorite.repository;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.favorite.entity.Favorite;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByBoardAndEmployee(Board board, Employee employee);

    List<Favorite> findAllByEmployee(Employee employee);

    boolean existsByBoardAndEmployee(Board board, Employee employee);

    Slice<Favorite> findSliceByEmployeeAndBoard_DeletedOrderByIndexNumber(Employee employee, String deleted, PageRequest pageRequest);

    List<Favorite> findByEmployeeOrderByIndexNumber(Employee employee);


}
