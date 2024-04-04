package com.github.riset_backend.writeBoard.boardFile.entity;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.entity.Board;
import com.github.riset_backend.file.entity.File;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board_file")
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_file_id")
    private Long boardFileId;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;


    public BoardFile(Board board, File file) {
        this.board = board;
        this.file = file;
    }
}
