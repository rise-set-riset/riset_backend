package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.chating.dto.chatDto.DepartResponseDto;
import com.github.riset_backend.chating.dto.chatDto.JobGradeResponseDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEmployeeDto {

    private Long employeeNum;
    private String name;
    private String employeeId;
    private JobGradeResponseDto jobGrade;
    private Date dateOfJoin;
    private Integer totalAnnualLeave;
    private Integer salary;
    private DepartResponseDto depart;
    private String job;
    private String position;
    private String phone;
    private String address;

    public ProfileEmployeeDto(Employee employee) {
        this.employeeNum = employee.getEmployeeNo();
        this.name = employee.getName();
        this.employeeId = employee.getEmployeeId();
        this.jobGrade = employee.getJobGrade() !=null ? new JobGradeResponseDto(employee.getJobGrade()) : new JobGradeResponseDto();
        this.dateOfJoin = employee.getDateOfJoin();
        this.totalAnnualLeave = employee.getTotalAnnualLeave();
        this.salary = employee.getSalary();
        this.depart = employee.getDepartment() != null ? new DepartResponseDto(employee.getDepartment()) : new DepartResponseDto();
        this.job = employee.getJob();
        this.position = employee.getPosition();
        this.phone = employee.getTelNumber();
        this.address = employee.getAddress();
    }
}
