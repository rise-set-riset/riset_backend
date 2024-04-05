package com.github.riset_backend.schedules.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedule")
@NoArgsConstructor
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_no")
    private Long scheduleNo;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_no")
    private Company company;

    @Column(name = "start_dt")
    private LocalDateTime startDate;

    @Column(name = "end_dt")
    private LocalDateTime endDate;

    @Column(name = "title")
    private String title;

    @Column(name = "writer")
    private String writer;


    @Column(name = "content")
    private String content;


    @Builder
    public Schedule(Company company, LocalDateTime startDate, LocalDateTime endDate, String title, String writer, String content) {
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}