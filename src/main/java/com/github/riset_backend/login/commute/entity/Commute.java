package com.github.riset_backend.login.commute.entity;

import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "commute")
public class Commute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commute_no")
    private Long commuteNo;

    @Column(name = "commute_start")
    private LocalTime commuteStart;

    @Column(name = "commute_end")
    private LocalTime commuteEnd;

    @Column(name = "status")
    private String status;

    @Column(name = "work_hours")
    private Integer workHours;

    @ManyToOne
    @JoinColumn(name = "employee_no", referencedColumnName = "employee_no")
    private Employee employee;



    //반차 신청시 퇴근시간 업데이트
    public void updateCommuteEnd(LocalTime commuteEnd) {
        this.commuteEnd = commuteEnd;
    }
}
