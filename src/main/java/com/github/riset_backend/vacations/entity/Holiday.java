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

    // 반차 여부
    @Column(name = "half_day")
    private Boolean halfDay;

    @Column(name = "leaveStatus")
    private LeaveType leaveStatus;

    //연차 일 경우
    public void addAll(Employee employee, LocalDateTime startDate, String leaveStatus, String comment, boolean halfDay, String status) {
        this.employee = employee;
        this.startDate = startDate;
        this.leaveStatus = LeaveType.valueOf(leaveStatus);
        this.comment = comment;
        this.halfDay = halfDay;
        this.status = Status.valueOf(status);
    }

    //반차일 경우
    public void addHalf(Employee employee, LocalDateTime startDate, String leaveStatus, LocalDateTime endDate, String comment, Boolean halfDay, String status) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveStatus = LeaveType.valueOf(leaveStatus);
        this.comment = comment;
        this.halfDay = halfDay;
        this.status = Status.valueOf(status);
    }

}
