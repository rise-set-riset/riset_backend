package com.github.riset_backend.schedules.repository;


import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.QEmployee;
import com.github.riset_backend.schedules.entity.QSchedule;
import com.github.riset_backend.vacations.entity.QHoliday;
import com.mongodb.client.model.Filters;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;



@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;


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



}