package com.github.riset_backend.vacations.dto;

import java.time.LocalDateTime;

public record HolidayRequest(

        String department, //부서
        LocalDateTime startTime, //시작일자
        LocalDateTime endTime, //끝나는 일자
        boolean isHalfDay


) {
}
