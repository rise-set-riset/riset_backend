package com.github.riset_backend.schedules.dto.employee;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class EmployeeDTO {
    private Long id;
    private String name;
    private String department;
    private String position;
    private String image;
    private String status;
    private Boolean editable;
    private Boolean isAnnualLeave;
    private Boolean isHalfDayLeave;
    private List<HalfDayLeaveDTO> halfDayLeave;
    private List<AnnualLeaveDTO> annualLeave;
    private List<ScheduleDTO> scheduleDTOs;



}
