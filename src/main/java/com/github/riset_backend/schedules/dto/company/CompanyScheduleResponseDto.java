package com.github.riset_backend.schedules.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;

public record CompanyScheduleResponseDto(


        @Schema(description = "스케줄 id", example = "1")
        Long scheduleNo,

        @Schema(description = "글쓴이", example = "홍길동")
        String writer,

        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "내용", example = "아무 냐용")
        String content,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        String startDate,

        @Schema(description = "끝나는 날짜", example = "2023-04-02 09:00")
        String endDate,

        @Schema(description = "색상", example = "color, #fffff")
        String color
) {
}
