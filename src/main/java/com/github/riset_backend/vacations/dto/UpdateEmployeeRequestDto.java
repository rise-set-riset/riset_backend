package com.github.riset_backend.vacations.dto;

import java.time.LocalDateTime;

public record UpdateEmployeeRequestDto(
        Long leaveNo,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String comment,
        boolean isHalfDay
) {
}