package com.github.riset_backend.schedules.dto.company;

import java.time.LocalDateTime;

public record CompanyResponseDto(
        Long scheduleNo,
        LocalDateTime start,
        LocalDateTime end,
        String title,
        String writer,
        String content,
        String color
) {


}