package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.vacations.dto.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDTO {
    private Long scheduleNo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private Status status;

    public ScheduleDTO(Long scheduleNo, LocalDateTime startDate, LocalDateTime endDate, String content, Status status) {
        this.scheduleNo = scheduleNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.status = status;
    }
}
