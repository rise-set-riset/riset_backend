package com.github.riset_backend.schedules.repository;
;
import com.github.riset_backend.login.employee.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ScheduleRepositoryCustom {
    Long updateScheduleDynamic(Long scheduleId, Map<String, String> fieldValues, LocalDateTime startTime, LocalDateTime endTime);
}
