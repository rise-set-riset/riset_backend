package com.github.riset_backend.vacations.dto;

public record HolidayResponse(
        java.time.LocalDateTime start,
        Status status,
        Long leaveType

) {
}
