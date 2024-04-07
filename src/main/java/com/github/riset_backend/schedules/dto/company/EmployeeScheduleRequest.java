package com.github.riset_backend.schedules.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record EmployeeScheduleRequest(
        @Schema(description = "id", example = "1")
        Long id,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        LocalDateTime startDate,

        @Schema(description = "끝나는 날짜", example = "2023-04-01 09:00")
        LocalDateTime endDate,

        @Schema(description = "일정 제목", example = "나는 짱이다")
        String title,

        @Schema(description = "작성자", example = "김승윤")
        String writer,

        @Schema(description = "내용", example = "소중한 프엔, 귀중한 백엔")
        String content

) {

}
