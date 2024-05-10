package com.github.riset_backend.vacations.repository;

import com.github.riset_backend.schedules.dto.employee.schedulesALL.EmployeeAnnualLeaveResponse;
import com.github.riset_backend.schedules.dto.employee.schedulesALL.EmployeeHalfLeaveResponse;
import com.github.riset_backend.vacations.entity.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepositoryCustom {
    List<EmployeeHalfLeaveResponse> getHolidayDay(Long id,LocalDate data);
    List<EmployeeAnnualLeaveResponse> getAnnualDay(Long id,LocalDate data);
}
