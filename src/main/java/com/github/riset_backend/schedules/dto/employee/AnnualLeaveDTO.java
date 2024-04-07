package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.Status;

import java.time.LocalDateTime;

public record AnnualLeaveDTO(
        Long leaveNo,
        LocalDateTime leaveStartDate,
        String leaveComment,
        Status leaveStatus

) {

}
