package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.dto.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HolidaysDTO {
    //휴가 아이디
    private Long HolidayNo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private Boolean halfStatus;
    private Status status;


}
