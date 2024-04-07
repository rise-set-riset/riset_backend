package com.github.riset_backend.schedules.dto.company;

import java.time.LocalDateTime;

public record UpdateComScheduleDto(
        Long ScheduleId,
        String title,
        String content,
        String writer,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String color

) {
}
