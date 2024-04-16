package com.github.riset_backend.schedules.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;


public record CompanyUpdateDateTimeDto(
        @Schema(description = "스케줄 id", example = "1")
        Long scheduleNo,

        @Schema(description = "일정 제목", example = "나는 짱이다")
        String title,

        @Schema(description = "내용", example = "소중한 프엔, 귀중한 백엔")
        String content,

        @Schema(description = "작성자", example = "김승윤")
        String writer,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        String start,

        @Schema(description = "끝나는 날짜", example = "2023-04-01 09:00")
        String end,

        @Schema(description = "색상", example = "color, #fffff")
        String color



) {
}
