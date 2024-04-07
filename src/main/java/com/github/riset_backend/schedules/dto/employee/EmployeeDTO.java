package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.entity.Holiday;
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
    private Boolean onLeave;
    private Boolean editable;
    private List<HolidaysDTO> halfDayLeave;
    private List<ScheduleDTO> scheduleDTOs;



}
