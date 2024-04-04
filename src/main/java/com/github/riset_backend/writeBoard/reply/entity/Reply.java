package com.github.riset_backend.writeBoard.reply.entity;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reply")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_no")
    private Long replyNo;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted")
    private String deleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    public Reply(Board board, Employee employee, String content) {
        this.board = board;
        this.employee = employee;
        this.content = content;
    }
}
