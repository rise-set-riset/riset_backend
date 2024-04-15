package com.github.riset_backend.schedules.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;


public record CompanyScheduleRequestDto(

        Long scheduleNo,
        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        String start,
        @Schema(description = "끝나는 날짜", example = "2023-04-02 09:00")
        String end,
        @Schema(description = "일정 제목", example = "나는 짱이다")
        String title,
        @Schema(description = "작성자", example = "김승윤")
        String writer,
        @Schema(description = "내용", example = "소중한 프엔, 귀중한 백엔")
        String content,
        @Schema(description = "white, #fffff", example = "색 또는 코드")
        String color
) {

}
