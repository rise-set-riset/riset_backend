package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.login.department.entity.Department;
import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDto {

    private JobGrade jobGrade;
    private String dateOfJoin;
    private Integer totalHoliday;
    private Integer salary;
    private Department department;
    private String position;
    private String job;

    public EmployeeUpdateDto(ProfileUpdateDto dto) {
        this.jobGrade = jobGrade;
        this.dateOfJoin = dto.getDateOfJoin();
        this.totalHoliday = dto.getTotalHoliday();
        this.salary = dto.getSalary();
        this.department = department;
        this.position = dto.getPosition();
        this.job = dto.getJob();
    }


}
