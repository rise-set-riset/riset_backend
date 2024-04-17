package com.github.riset_backend.vacations.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record HolidayRequest(

        LocalDateTime startTime, //시작일자
        LocalDateTime endTime, //시작일자

        boolean isHalfDay


) {

}
