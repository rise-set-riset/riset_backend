package com.github.riset_backend.vacations.dto;

import java.time.LocalDateTime;

public record RequestHoliday(
        String department, //부서

        LocalDateTime startDateTime, //시작일자
        LocalDateTime endDateTime, //끝나는 일자
        String type,
        String comment, //설명

        LeaveType status, // 승인값, 상태 처음엔 무조건 대기로

        boolean isHalfDay


) {
}
