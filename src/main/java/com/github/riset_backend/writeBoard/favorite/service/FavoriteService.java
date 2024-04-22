package com.github.riset_backend.writeBoard.favorite.service;

import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.board.repository.BoardRepository;
import com.github.riset_backend.writeBoard.favorite.dto.FavoriteResponseDto;
import com.github.riset_backend.writeBoard.favorite.dto.FavoriteUpdateRequestDto;
import com.github.riset_backend.writeBoard.favorite.entity.Favorite;
import com.github.riset_backend.writeBoard.favorite.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public List<FavoriteResponseDto> createFavoriteBoard(Long boardNo, Employee employee) {
        Integer index_number;

        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );

        boolean hasFavorite = favoriteRepository.existsByBoardAndEmployee(board, employee);

        if(hasFavorite) {
            throw new BusinessException(ErrorCode.EXIST_FAVORITE);
        }

        List<Favorite> favorites = favoriteRepository.findAllByEmployee(employee);

        if(favorites.isEmpty()) {
            index_number = 1;
        } else {
            index_number = favorites.get(favorites.size()-1).getIndexNumber() + 1;
        }

        Favorite favorite = new Favorite(board, employee, index_number);
        Favorite newFavorite = favoriteRepository.save(favorite);

        List<Favorite> newFavorites = favoriteRepository.findAllByEmployee(employee);

//        PageRequest pageRequest = PageRequest.of(0, 5);
//        Slice<Favorite> newFavorites = favoriteRepository.findSliceByEmployeeAndBoard_DeletedOrderByIndexNumber(employee, null ,pageRequest);

        return newFavorites.stream().map(FavoriteResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<FavoriteResponseDto> getAllFavoriteBoard(Employee employee, int page, int size, String title) {
        PageRequest pageRequest = PageRequest.of(page, size);
//        Slice<Favorite> favorites = favoriteRepository.findSliceByEmployeeAndBoard_DeletedOrderByIndexNumber(employee, null, pageRequest);

        Slice<Favorite> favorites = favoriteRepository.findSliceByEmployeeAndBoard_TitleContainingAndBoard_DeletedOrderByIndexNumber(employee, title, null, pageRequest);

        return favorites.stream().map(FavoriteResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<FavoriteResponseDto> updateFavoriteBoard(Long boardNo, Employee employee, FavoriteUpdateRequestDto favoriteUpdateRequestDto) {

        Board board = boardRepository.findByBoardNo(boardNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_BOARD)
        );

        Favorite favorite = favoriteRepository.findByBoardAndEmployee(board, employee).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_FAVORITE)
        );

        Integer prevIndex = favorite.getIndexNumber();
        Integer changeIndex = favoriteUpdateRequestDto.getIndex();


        List<Favorite> favorites = favoriteRepository.findByEmployeeOrderByIndexNumber(employee);

        if(changeIndex < prevIndex) {
            favorite.setIndexNumber(favoriteUpdateRequestDto.getIndex());

            favorites.forEach(eachFavorite -> {
                if(eachFavorite.getIndexNumber() >= changeIndex && eachFavorite.getIndexNumber() < prevIndex && !Objects.equals(favorite.getFavoriteId(), eachFavorite.getFavoriteId())) {
                    Favorite updateFavorite = favoriteRepository.findByBoardAndEmployee(eachFavorite.getBoard(), employee).orElseThrow(
                            () -> new BusinessException(ErrorCode.NOT_FOUND_FAVORITE)
                    );
                    updateFavorite.setIndexNumber(updateFavorite.getIndexNumber() + 1);
                }
            });
        } else {
            favorite.setIndexNumber(favoriteUpdateRequestDto.getIndex());

            favorites.forEach(eachFavorite -> {
                if( eachFavorite.getIndexNumber() <= changeIndex && eachFavorite.getIndexNumber() > prevIndex  && !Objects.equals(favorite.getFavoriteId(), eachFavorite.getFavoriteId())) {
                    Favorite updateFavorite = favoriteRepository.findByBoardAndEmployee(eachFavorite.getBoard(), employee).orElseThrow(
                            () -> new BusinessException(ErrorCode.NOT_FOUND_FAVORITE)
                    );
                    updateFavorite.setIndexNumber(updateFavorite.getIndexNumber() - 1);
                }
            });
        }



        List<Favorite> newFavorites = favoriteRepository.findByEmployeeOrderByIndexNumber(employee);
        return newFavorites.stream().map(FavoriteResponseDto::new).collect(Collectors.toList());

    }

    @Transactional
    public List<FavoriteResponseDto> deleteFavoriteBoard(Long favoriteId, Employee employee) {
       Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(
               () -> new BusinessException(ErrorCode.NOT_FOUND_FAVORITE)
       );

       List<Favorite> favorites = favoriteRepository.findAllByEmployee(employee);
       favorites.forEach(eachFavorite -> {
          if(eachFavorite.getIndexNumber() > favorite.getIndexNumber()) {
              Favorite updateFavorite = favoriteRepository.findByBoardAndEmployee(eachFavorite.getBoard(), employee).orElseThrow(
                      () -> new BusinessException(ErrorCode.NOT_FOUND_FAVORITE)
              );
              updateFavorite.setIndexNumber(updateFavorite.getIndexNumber() - 1);
          }
       });

       favoriteRepository.delete(favorite);

       List<Favorite> newFavorites = favoriteRepository.findAllByEmployee(employee);

//        PageRequest pageRequest = PageRequest.of(0, 5);
//        Slice<Favorite> newFavorites = favoriteRepository.findSliceByEmployeeAndBoard_DeletedOrderByIndexNumber(employee, null ,pageRequest);

       return newFavorites.stream().map(FavoriteResponseDto::new).collect(Collectors.toList());

    }
}
