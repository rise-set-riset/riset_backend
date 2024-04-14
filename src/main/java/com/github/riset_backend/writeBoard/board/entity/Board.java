package com.github.riset_backend.writeBoard.board.entity;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.dto.BoardRequestDto;
import com.github.riset_backend.writeBoard.boardFile.entity.BoardFile;
import com.github.riset_backend.writeBoard.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "board")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Board  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Column(name = "deleted")
    private String deleted;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardFile> boardFiles;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies;

    public static Board boardRequestToBoard (BoardRequestDto boardRequestDto, Employee employee) {
        return Board.builder()
                .employee(employee)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .build();
    }

    public Board updateBoard(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        return this;
    }
}
