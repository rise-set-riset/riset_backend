package com.github.riset_backend.schedules.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.schedules.dto.company.TestDto;
import com.github.riset_backend.vacations.dto.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Setter
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
    public void update(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    //직원 일정추가
    public void addEmployee(Employee employee, Company company, LocalDateTime startDate, LocalDateTime endDate, String title) {
        this.employee = employee;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }


    public void addTime(String startDate, String endDate, String title, String writer, String content, String color, Employee employee) {
        DateTimeFormatter formatter;
        if (startDate.contains("T")) {
            // 'T'를 포함하고 있으면 LocalDateTime으로 파싱
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.startDate = LocalDateTime.parse(startDate, formatter);
        } else {
            // 'T'를 포함하고 있지 않으면 LocalDate로 파싱한 후 시간을 00:00으로 설정
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
            this.startDate = parsedStartDate.atStartOfDay();
        }

        if (endDate.contains("T")) {
            // 'T'를 포함하고 있으면 LocalDateTime으로 파싱
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            // "24:00"을 "23:59"로 변경하여 처리
            endDate = endDate.replace("24:00", "23:59");
            this.endDate = LocalDateTime.parse(endDate, formatter);
        } else {
            // 'T'를 포함하고 있지 않으면 LocalDate로 파싱한 후 시간을 23:59로 설정
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedEndDate = LocalDate.parse(endDate, formatter);
            this.endDate = parsedEndDate.atTime(23, 59);
        }
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.color = color;
        this.employee = employee;
    }


}