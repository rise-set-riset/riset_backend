package com.github.riset_backend.schedules.dto.employee;

import com.github.riset_backend.login.employee.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDTO {
    private Long scheduleNo;
    private Employee employee;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private String status;

    public ScheduleDTO(Long scheduleNo, LocalDateTime startDate, LocalDateTime endDate, String content, String status) {
        this.scheduleNo = scheduleNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.status = status;
    }
}
