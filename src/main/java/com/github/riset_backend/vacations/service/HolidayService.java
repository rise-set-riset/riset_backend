package com.github.riset_backend.vacations.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.dto.RequestHoliday;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.dto.Status;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HolidayService {


    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;

    //연차 반차 처리
    public RequestHoliday addHoliday(RequestHoliday request, CustomUserDetails user) {
        Employee employee = employeeRepository.findByEmployeeId(user.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        boolean isHalfDay = request.isHalfDay();
        //true 반차, false 연차
        String type = isHalfDay ? LeaveType.HALF_DAY_LEAVE.name() : LeaveType.ANNUAL_LEAVE.name();

        Holiday holidayEntity;

        if (LeaveType.ANNUAL_LEAVE.name().equals(type)) {
            // 연차 처리
            holidayEntity = new Holiday();
            holidayEntity.addAll(employee, request.startDateTime(), LeaveType.ANNUAL_LEAVE, request.comment(),  Status.PENDING.name());
        } else if (LeaveType.HALF_DAY_LEAVE.name().equals(type)) {
            // 반차 처리
            holidayEntity = new Holiday();
            holidayEntity.addHalf(employee, request.startDateTime(), LeaveType.HALF_DAY_LEAVE, request.endDateTime(), request.comment(), Status.PENDING.name());
        } else {
            // 예외 처리 또는 기본 동작 설정
            throw new IllegalArgumentException("Invalid leave type: " + type);
        }

        // 휴가를 데이터베이스에 저장
        holidayRepository.save(holidayEntity);

        // 처리된 휴가에 대한 정보를 담은 객체 반환
        return request;
    }
}

