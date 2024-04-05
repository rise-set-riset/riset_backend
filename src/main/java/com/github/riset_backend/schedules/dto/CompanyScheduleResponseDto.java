package com.github.riset_backend.schedules.dto;

import com.github.riset_backend.schedules.entity.Schedule;

import java.time.format.DateTimeFormatter;

public record CompanyScheduleResponseDto(
        Long scheduleNo,
        String writer,
        String title,
        String content,
        String startDate,
        String endDate
) {}
