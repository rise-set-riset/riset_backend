package com.github.riset_backend.writeBoard.favorite.dto;

import com.github.riset_backend.file.dto.FileResponseDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.dto.BoardResponseDto;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.favorite.entity.Favorite;
import com.github.riset_backend.writeBoard.reply.dto.ReplyResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDto {

    private Long id;
    private Employee employee;
    private BoardResponseDto boardResponseDto;

    public FavoriteResponseDto(Favorite favorite) {
        this.id = favorite.getFavoriteId();
        this.employee = favorite.getEmployee();
        this.boardResponseDto = new BoardResponseDto(favorite.getBoard());
    }
}
