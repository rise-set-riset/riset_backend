package com.github.riset_backend.schedules.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record EmployeeAddScheduleRequestDTO(

        Long scheduleNo,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        LocalDateTime startDateTime,

        @Schema(description = "끝나는 날짜", example = "2023-04-01 09:00")
        LocalDateTime endDateTIme,

        @Schema(description = "내용", example = "소중한 프엔, 귀중한 백엔")
        String content,

        @Schema(description = "일정 제목", example = "나는 짱이다")
        String title

) {

}
