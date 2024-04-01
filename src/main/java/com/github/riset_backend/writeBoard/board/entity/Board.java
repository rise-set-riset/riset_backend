package com.github.riset_backend.writeBoard.board.entity;

import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long BoardNo;

    @ManyToOne
    @JoinColumn(name = "empployee_no")
    private Employee employee;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}
