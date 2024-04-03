package com.github.riset_backend.writeBoard.board.dto;

import com.github.riset_backend.file.dto.FileResponseDto;
import com.github.riset_backend.file.entity.File;
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

    private Long id;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime date;
    private String img;
    private List<FileResponseDto> files;
    private List<ReplyResponseDto> replies;

    public BoardResponseDto(Board board) {
        this.id = board.getBoardNo();
        this.title = board.getTitle();
        this.writer = board.getEmployee().getName();
        this.content = board.getContent();
        this.date = board.getCreateAt();
        this.img = null;
//        this.files = board.getBoardFiles().stream().map(BoardFile::getFile).map(FileResponseDto::new).collect(Collectors.toList());
//        this.replies = board.getReplies().stream().map(ReplyResponseDto::new).toList();
    }

    public static BoardResponseDto ToBoardResponseDto (Board board, List<File> files, String writer) {
        return BoardResponseDto.builder()
                .id(board.getBoardNo())
                .title(board.getTitle())
                .writer(writer)
                .content(board.getContent())
                .date(board.getCreateAt())
                .files(files.stream().map(FileResponseDto::new).collect(Collectors.toList()))
                .build();
    }

}
