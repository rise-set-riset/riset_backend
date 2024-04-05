package com.github.riset_backend.schedules.dto;

public record CompanyScheduleResponseDto(
        Long id,
        String startDate,
        String endDate,
        String title,
        String writer,
        String content
) {

}
