package com.github.riset_backend.vacations.repository;

import com.github.riset_backend.schedules.dto.employee.EmployeeAddScheduleResponseDTO;
import com.github.riset_backend.schedules.dto.employee.schedulesALL.EmployeeAnnualLeaveResponse;
import com.github.riset_backend.schedules.dto.employee.schedulesALL.EmployeeHalfLeaveResponse;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.entity.QHoliday;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class HolidayRepositoryImpl implements HolidayRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<EmployeeHalfLeaveResponse> getHolidayDay(Long id, LocalDate data) {
        QHoliday holidays = QHoliday.holiday;

        List<Holiday> holidayList = queryFactory
                .selectFrom(holidays)
                .where(holidays.startDate.year().eq(data.getYear())
                        .and(holidays.employee.employeeNo.eq(id))
                        .and(holidays.leaveStatus.eq(LeaveType.HALF_DAY_LEAVE))
                        .and(holidays.startDate.month().eq(data.getMonthValue()))
                        .and(holidays.startDate.dayOfMonth().eq(data.getDayOfMonth())))
                .orderBy(holidays.startDate.asc())
                .fetch();

        return holidayList.stream()
                .map(e -> new EmployeeHalfLeaveResponse(
                        formatTime(e.getStartDate()),
                        formatTime(e.getEndDate()),
                        e.getComment()
                )).toList();


    }


    @Override
    public List<EmployeeAnnualLeaveResponse> getAnnualDay(Long id, LocalDate data) {
        QHoliday holidays = QHoliday.holiday;

        List<Holiday> holidayList = queryFactory
                .select(holidays)
                .from(holidays)
                .where(holidays.startDate.year().eq(data.getYear())
                        .and(holidays.employee.employeeNo.eq(id))
                        .and(holidays.leaveStatus.eq(LeaveType.ANNUAL_LEAVE))
                        .and(holidays.startDate.month().eq(data.getMonthValue()))
                        .and(holidays.startDate.dayOfMonth().eq(data.getDayOfMonth())))
                .orderBy(holidays.startDate.asc())
                .fetch();

        return holidayList.stream()
                .map(e -> new EmployeeAnnualLeaveResponse(
                        formatTime(e.getStartDate()),
                        e.getComment()
                )).toList();


    }

    private String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
