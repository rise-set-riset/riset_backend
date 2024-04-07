package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record HalfDayLeaveDTO(
        //휴가 아이디
        Long halfNo,
        LocalDateTime halfStartDateTime,
        LocalDateTime HalfEndDateTime,
        String halfComment,
        Status halfStatus


) {


}
