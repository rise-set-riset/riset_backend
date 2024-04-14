package com.github.riset_backend.schedules.repository;



import com.github.riset_backend.schedules.dto.employee.schedulesALL.EmployeeSchedulesResponse;

import com.github.riset_backend.schedules.entity.QSchedule;
import com.github.riset_backend.schedules.entity.Schedule;

import com.querydsl.core.types.Path;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    //동적으로 값 수정
    @Override
    @Transactional
    public Long updateScheduleDynamic(Long scheduleId, Map<String, String> fieldValues, LocalDateTime startTime, LocalDateTime endTime) {
        QSchedule schedule = QSchedule.schedule;

        var updateClause = queryFactory
                .update(schedule)
                .where(schedule.scheduleNo.eq(scheduleId));

        // String 타입 필드만 동적으로 업데이트
        fieldValues.forEach((fieldName, newValue) -> {
            if (newValue != null) { // null 값 필터링
                PathBuilder<Object> pathBuilder = new PathBuilder<>(Object.class, schedule.getMetadata());
                Path<Object> fieldPath = pathBuilder.get(fieldName);
                updateClause.set(fieldPath, newValue);
            }
        });

        // LocalDateTime 타입 필드 업데이트
        if (startTime != null) {
            updateClause.set(schedule.startDate, startTime);
        }
        if (endTime != null) {
            updateClause.set(schedule.endDate, endTime);
        }

        return updateClause.execute(); // 쿼리 실행 및 영향 받은 행의 수 반환
    }


    //스케쥴 날짜에 맞춰서 찾아오기
    @Override
    public List<EmployeeSchedulesResponse> getScheduleDay(Long id, LocalDate data) {
        QSchedule schedules = QSchedule.schedule;

        List<Schedule> scheduleResponses = queryFactory
                .selectFrom(schedules)
                .where(schedules.startDate.year().eq(data.getYear())
                        .and(schedules.employee.employeeNo.eq(id))
                        .and(schedules.startDate.month().eq(data.getMonthValue()))
                        .and(schedules.startDate.dayOfMonth().eq(data.getDayOfMonth())))
                .orderBy(schedules.startDate.asc())
                .fetch();

        return scheduleResponses.stream()
                .map(e -> new EmployeeSchedulesResponse(
                        e.getScheduleNo(),
                        formatTime(e.getStartDate()),
                        formatTime(e.getEndDate()),
                        e.getTitle()
                ))
                .toList();
    }

    private String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

}