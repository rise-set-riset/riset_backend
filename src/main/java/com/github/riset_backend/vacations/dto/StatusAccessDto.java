package com.github.riset_backend.vacations.dto;

import java.time.LocalDateTime;



public record StatusAccessDto(
        Long leaveId,
        String userName,
        String departmentName,
        Status status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isHalfDay
) {}

