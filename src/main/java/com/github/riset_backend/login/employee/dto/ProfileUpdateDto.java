package com.github.riset_backend.login.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDto {

    private Long jobGradeId;
    private String dateOfJoin;
    private Integer totalHoliday;
    private Integer salary;
    private Long departId;
    private String position;
    private String job;

}
