package com.github.riset_backend.vacations.service;

import ch.qos.logback.classic.Logger;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;

import com.github.riset_backend.vacations.dto.*;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
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

        Holiday holiday = holidayRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        if (Objects.equals(holiday.getEmployee().getEmployeeNo(), user.getEmployee().getEmployeeNo())) {
            if (request.isHalfDay()) {
                holiday.leaveUpdate(user.getEmployee(), request.startDateTime(), request.endDateTime(), request.comment());
            } else {
                holiday.leaveUpdate(user.getEmployee(), request.startDateTime(), null, request.comment());
            }
        }

        holidayRepository.save(holiday);
    }

    //승인/반려 처리

    @Transactional
    public String accessEmployeeHoliday(CustomUserDetails user, StatusUpdateRequestDto request) {

        for (Long leaveNo : request.leaveNo()) {
            try {
                Holiday holiday = holidayRepository.findById(leaveNo)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_HOLIDAY));

                // 휴가를 요청한 직원과 현재 사용자가 일치하는지 확인
                if (!Objects.equals(holiday.getEmployee().getEmployeeNo(), user.getEmployee().getEmployeeNo())) {
                    // 요청한 휴가의 직원과 현재 사용자가 일치하지 않는 경우
                    throw new BusinessException(ErrorCode.NOT_USER);
                }

                // 요청된 상태가 APPROVED인 경우
                if (request.status().equals(Status.APPROVED)) {
                    // 휴가 상태를 APPROVED로 업데이트
                    holiday.statusUpdate(Status.APPROVED);
                    holidayRepository.save(holiday);
                }
                // 요청된 상태가 REJECTED인 경우
                else if (request.status().equals(Status.REJECTED)) {
                    // 휴가 상태를 REJECTED로 업데이트
                    holiday.statusUpdate(Status.REJECTED);
                    holidayRepository.save(holiday);
                }else if (request.status().equals(Status.PENDING)) {
                    holiday.statusUpdate(Status.PENDING);
                    holidayRepository.save(holiday);
                }
            } catch (BusinessException e) {
                // 예외 발생 시 로그 출력
                return "에러";
            }
        }

        return "처리완료";
    }




    //목록 조회
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