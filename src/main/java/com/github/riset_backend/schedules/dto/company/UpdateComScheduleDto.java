package com.github.riset_backend.schedules.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UpdateComScheduleDto(
        @Schema(description = "스케줄 id", example = "1")
        Long ScheduleId,

        @Schema(description = "일정 제목", example = "나는 짱이다")
        String title,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        LocalDateTime startDate,

        @Schema(description = "끝나는 날짜", example = "2023-04-01 09:00")
        LocalDateTime endDate

) {
}
