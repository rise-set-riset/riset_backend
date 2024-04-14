package com.github.riset_backend.writeBoard.favorite.dto;

import com.github.riset_backend.file.dto.FileResponseDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.dto.BoardResponseDto;
import com.github.riset_backend.writeBoard.board.dto.PostResponseDto;
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
    private Integer indexNumber;
    private Employee user;
    private PostResponseDto post;

    public FavoriteResponseDto(Favorite favorite) {
        this.id = favorite.getFavoriteId();
        this.indexNumber = favorite.getIndexNumber();
        this.user = favorite.getEmployee();
        this.post = new PostResponseDto(favorite.getBoard());
    }
}
