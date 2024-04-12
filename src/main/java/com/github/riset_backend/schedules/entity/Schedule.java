package com.github.riset_backend.schedules.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.vacations.dto.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

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
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_no")
    @JsonBackReference
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


    @Column(name = "status")
    private Status status;

    @Column(name = "color")
    private String color;

    @Builder
    //회사 일정 추가
    public Schedule(Company company, Optional<Employee> employee, LocalDateTime startDate, LocalDateTime endDate, String title, String writer, String content, String color) {
        this.company = company;
        this.employee = employee.orElse(null);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.color = color;
    }

    //직원 일정수정
    public void update(String title, String content, LocalDateTime startDate, LocalDateTime endDate, String writer) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer = writer;
    }

    //직원 일정등록
    public void addEmployee(Employee employee, Company company, LocalDateTime startDate, LocalDateTime endDate, String content, String title, Status status) {
        this.employee = employee;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
        this.status = status;
    }
}