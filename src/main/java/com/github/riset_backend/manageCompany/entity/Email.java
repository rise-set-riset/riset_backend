package com.github.riset_backend.manageCompany.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long email_id;

    private String code;

    private Long employeeId;

    @Builder
    public Email(Long employeeId, String code) {
        this.employeeId = employeeId;
        this.code = code;

    }
}
