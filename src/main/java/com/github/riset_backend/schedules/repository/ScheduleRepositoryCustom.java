package com.github.riset_backend.schedules.repository;


import com.github.riset_backend.schedules.dto.employee.schedulesALL.EmployeeSchedulesResponse;
import com.github.riset_backend.schedules.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ScheduleRepositoryCustom {
    Long updateScheduleDynamic(Long scheduleId, Map<String, String> fieldValues, LocalDateTime startTime, LocalDateTime endTime);

    List<EmployeeSchedulesResponse> getScheduleDay(Long id,LocalDate data);
}
