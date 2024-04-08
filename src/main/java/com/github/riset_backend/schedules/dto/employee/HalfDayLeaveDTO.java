package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record HalfDayLeaveDTO(

        @Schema(description = "반차 id", example = "1")
        Long halfNo,

        @Schema(description = "시작 날짜", example = "2023-04-01 09:00")
        LocalDateTime halfStartDateTime,

        @Schema(description = "끝나는 날짜", example = "2023-04-01 09:00")
        LocalDateTime HalfEndDateTime,

        @Schema(description = "설명", example = "반차")
        String halfComment,

        @Schema(description = "상태", example = "반려!!!!!!!!!")
        Status halfStatus


) {


}
