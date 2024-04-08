package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AnnualLeaveDTO(
        @Schema(description = "연차 id", example = "1")
        Long leaveNo,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        LocalDateTime leaveStartDate,

        @Schema(description = "내용", example = "제주도 가요~")
        String leaveComment,

        @Schema(description = "승인상태", example = "반려!!!")
        Status leaveStatus

) {

}
