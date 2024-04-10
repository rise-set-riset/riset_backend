package com.github.riset_backend.vacations.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;

import com.github.riset_backend.vacations.dto.*;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeLeaveSchedulesService {

    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;

    //연차 수정
    @Transactional
    public void updateEmployeeHoliday(CustomUserDetails user, UpdateEmployeeRequestDto request) {

        Holiday holiday = holidayRepository.findById(request.leaveNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        if (Objects.equals(holiday.getEmployee().getEmployeeNo(), user.getEmployee().getEmployeeNo())) {
            if (request.isHalfDay()) {
                holiday.leaveUpdate(user.getEmployee(), request.startDateTime(), request.endDateTime(), request.comment());
            } else {
                holiday.leaveUpdate(user.getEmployee(), request.startDateTime(), null, request.comment());
            }
        }
        holidayRepository.save(holiday);
    }

    @Transactional
    public void accessEmployeeHoliday(CustomUserDetails user, StatusUpdateRequestDto request) {
        Holiday holiday = holidayRepository.findById(request.leaveNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        if (Objects.equals(holiday.getEmployee().getEmployeeNo(), user.getEmployee().getEmployeeNo())) {
            holiday.statusUpdate(request.status());
        } else {
            throw new BusinessException(ErrorCode.NOT_USER);
        }
    }

    public List<StatusAccessDto> getEmployeeHoliday(CustomUserDetails user, Status status) {
        if (status.equals(Status.PENDING)) {
            return employeeHolidayGet(user, Status.PENDING);
        } else if (status.equals(Status.APPROVED)) {
            return employeeHolidayGet(user, Status.APPROVED);
        } else if (status.equals(Status.REJECTED)) {
            return employeeHolidayGet(user, Status.REJECTED);
        }
        return Collections.emptyList();

    }

    public List<StatusAccessDto> employeeHolidayGet(CustomUserDetails user, Status status) {
        List<Holiday> holidays = holidayRepository.findByStatus(status); // 해당 상태에 따라 필터링된 휴가 목록을 얻음

        Employee employee = employeeRepository.findByEmployeeNo(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        String departmentName = employee.getDepartment().getDeptName();
        List<StatusAccessDto> dto = holidays.stream()
                .map(holiday -> new StatusAccessDto(
                        holiday.getLeaveNo(),
                        holiday.getEmployee().getName(),
                        departmentName,
                        holiday.getStatus(),
                        holiday.getStartDate(),
                        holiday.getEndDate(),
                        holiday.getLeaveStatus() == LeaveType.HALF_DAY_LEAVE
                )).toList();

        return dto;
    }
}