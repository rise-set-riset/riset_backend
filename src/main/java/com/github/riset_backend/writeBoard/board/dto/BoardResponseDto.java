package com.github.riset_backend.writeBoard.board.dto;

import com.github.riset_backend.file.dto.FileResponseDto;
import com.github.riset_backend.file.entity.File;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.boardFile.entity.BoardFile;
import com.github.riset_backend.writeBoard.reply.dto.ReplyResponseDto;
import com.github.riset_backend.writeBoard.reply.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    public Employee user;
    public PostResponseDto post;

    public BoardResponseDto(Board board, boolean like) {
        this.user = board.getEmployee();
        this.post = new PostResponseDto(board, like);
    }

    public BoardResponseDto(Board board) {
        this.user = board.getEmployee();
        this.post = new PostResponseDto(board);
    }

//    public static BoardResponseDto ToBoardResponseDto (Board board, List<File> files, String writer) {
//        return BoardResponseDto.builder()
//                .id(board.getBoardNo())
//                .title(board.getTitle())
//                .writer(writer)
//                .content(board.getContent())
//                .date(board.getCreateAt())
//                .files(files.stream().map(FileResponseDto::new).collect(Collectors.toList()))
//                .build();
//    }

//    public static BoardResponseDto ToBoardResponseDto (Board board, List<File> files, String writer) {
//        return BoardResponseDto.builder()
//                .id(board.getBoardNo())
//                .title(board.getTitle())
//                .writer(writer)
//                .content(board.getContent())
//                .date(board.getCreateAt())
//                .files(files.stream().map(FileResponseDto::new).collect(Collectors.toList()))
//                .build();
//    }

}
