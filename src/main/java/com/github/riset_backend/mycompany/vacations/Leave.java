package com.github.riset_backend.mycompany.vacations;



import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "leave")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_no")
    private Long leaveNo;

    @Column(name = "employee_no")
    private Long employeeNo;

    @Column(name = "start_dt")
    private Date startDate;

    @Column(name = "end_dt")
    private Date endDate;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

}