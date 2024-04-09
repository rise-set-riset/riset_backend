package com.github.riset_backend.login.employee.entity;

import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.department.entity.Department;
import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.schedules.entity.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_no")
    private Long employeeNo;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employee_password")
    private String password;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_phone")
    private String phoneNumber;

    @Column(name = "employee_address")
    private String address;

    @Column(name = "employee_birth")
    private String birth;

    @Column(name = "employee_job")
    private String job;

    @Column(name = "employee_position")
    private String position;

    @Column(name = "zip_code")
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "department_no", referencedColumnName = "department_no")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "company_no", referencedColumnName = "company_no")
    private Company company;

    @OneToOne
    @JoinColumn(name = "grade_no", referencedColumnName = "grade_no")
    private JobGrade jobGrade;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Schedule> employeeScheduleList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Holiday> holidays;

    // 반차 여부를 나타내는 필드
    @Transient
    private Boolean halfDayLeave;



}
