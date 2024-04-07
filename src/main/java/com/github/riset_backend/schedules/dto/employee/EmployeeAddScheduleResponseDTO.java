package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.Status;

import java.time.LocalDateTime;

public record EmployeeAddScheduleResponseDTO(
        Long employeeId,
        Long companyId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTIme,
        String content,
        String title,
        Status status

) {

}
