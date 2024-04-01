package com.github.riset_backend.mycompany.ranks;

import jakarta.persistence.*;


@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_no")
    private Long positionNo;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "salary")
    private Double salary;

}