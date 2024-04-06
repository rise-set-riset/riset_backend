package com.github.riset_backend.writeBoard.favorite.entity;


import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @Column(name = "index_number")
    private Integer indexNumber;

    public Favorite(Board board, Employee employee, Integer indexNumber) {
        this.board = board;
        this.employee = employee;
        this.indexNumber = indexNumber;
    }
}
