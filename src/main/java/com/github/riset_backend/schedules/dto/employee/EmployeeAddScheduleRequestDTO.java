package com.github.riset_backend.schedules.dto.employee;

import java.time.LocalDateTime;

public record EmployeeAddScheduleRequestDTO(
        LocalDateTime startDateTime,
        LocalDateTime endDateTIme,
        String content,
        String title

) {

}
