package com.github.riset_backend.schedules.dto.company;

public record CompanyScheduleResponseDto(
        Long scheduleNo,
        String writer,
        String title,
        String content,
        String startDate,
        String endDate,
        String color
) {}
