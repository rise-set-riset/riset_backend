package com.github.riset_backend.schedules.dto.company;

import java.time.LocalDateTime;

public record CompanyResponseDto(
        Long scheduleNo,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String title,
        String writer,
        String content,
        String color
) {

    public Long getScheduleNo() {
        return this.scheduleNo;
    }

}