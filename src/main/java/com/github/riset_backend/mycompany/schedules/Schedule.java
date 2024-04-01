package com.github.riset_backend.mycompany.schedules;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "schedule")
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
    private Date startDate;

    @Column(name = "end_dt")
    private Date endDate;

    @Column(name = "title")
    private String title;

}