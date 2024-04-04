package com.github.riset_backend.schedules.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_no")
    private Long scheduleNo;

    @Column(name = "employee_no")
    private Long employeeNo;

    @Column(name = "company_no")
    private Long companyNo;

    @Column(name = "start_dt")
    private LocalDateTime startDate;

    @Column(name = "end_dt")
    private LocalDateTime endDate;

    @Column(name = "title")
    private String title;

    @Column(name = "writer")
    private String writer;

    @Lob
    @Column(name = "content")
    private String content;

//      .companyNo(company)
//                .title(request.title())
//            .startDate(request.startDate())
//            .writer(request.writer())
//            .content(request.content())

    @Builder
    public Schedule(Long companyNo, LocalDateTime startDate, LocalDateTime endDate, String title, String writer, String content) {
        this.companyNo = companyNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public void updateSchedule(LocalDateTime startDate, LocalDateTime endDate, String title, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }


}