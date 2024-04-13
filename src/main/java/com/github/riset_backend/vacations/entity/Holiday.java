package com.github.riset_backend.vacations.entity;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.dto.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "holiday")
public class Holiday {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_no")
    private Long leaveNo;


    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @Column(name = "start_dt")
    private LocalDateTime startDate;


    @Column(name = "end_dt")
    private LocalDateTime endDate;

//    // 휴가 유형
//    @Column(name = "type")
//    private String type;

    // 승인상태
    @Column(name = "status")
    private Status status;

    // 설명
    @Column(name = "comment")
    private String comment;


    @Column(name = "leaveStatus")
    private LeaveType leaveStatus;

    //휴가 일수
    private Long vacationsDay;


    //연차 일 경우
    public void addAll(Employee employee, LocalDateTime startDate, LeaveType leaveStatus, String comment) {
        this.employee = employee;
        this.startDate = startDate;
        this.leaveStatus = leaveStatus;
        this.comment = comment;
        this.status = Status.valueOf(String.valueOf(status));
    }

    // 반차일 경우
    public void addHalf(Employee employee, LocalDateTime startDate, LeaveType leaveStatus, LocalDateTime endDate, String comment) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveStatus = leaveStatus;
        this.comment = comment;
        this.status = Status.valueOf(String.valueOf(status));
    }

    //연차/반차 수정
    public void leaveUpdate(Employee employee, LocalDateTime startDate, LocalDateTime endDate, String comment) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    public void statusUpdate(Status status) {
        this.status = status;
    }

}
