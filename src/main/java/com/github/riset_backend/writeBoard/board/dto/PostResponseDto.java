package com.github.riset_backend.writeBoard.board.dto;

import com.github.riset_backend.file.dto.FileResponseDto;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.writeBoard.boardFile.entity.BoardFile;
import com.github.riset_backend.writeBoard.reply.dto.ReplyResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;
    private boolean like;
    private List<FileResponseDto> files;
    private List<ReplyResponseDto> comment;

    public PostResponseDto(Board board, boolean like) {
        this.id = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.date = board.getCreateAt();
        this.like = true;
        this.files = board.getBoardFiles().stream().map(BoardFile::getFile).map(FileResponseDto::new).collect(Collectors.toList());;
        this.comment =  board.getReplies().stream().map(ReplyResponseDto::new).toList();
    }

    public PostResponseDto(Board board) {
        this.id = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.date = board.getCreateAt();
        this.files = board.getBoardFiles().stream().map(BoardFile::getFile).map(FileResponseDto::new).collect(Collectors.toList());;
        this.comment =  board.getReplies().stream().map(ReplyResponseDto::new).toList();
    }
}
