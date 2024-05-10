package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.commute.entity.Commute;
import com.github.riset_backend.login.commute.entity.CommutePlace;
import com.github.riset_backend.login.commute.repository.CommuteRepository;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.company.UpdateComScheduleDto;
import com.github.riset_backend.schedules.dto.employee.*;
import com.github.riset_backend.schedules.dto.employee.schedulesALL.*;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.repository.ScheduleRepository;

import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeSchedulesService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeSchedulesService.class);
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final CompanyRepository companyRepository;
    private final CommuteRepository commuteRepository;
    private final HolidayRepository holidayRepository;

    // 첫 진입 조회
    public List<EmployeeAll> getAllEmployees(LocalDate data, CustomUserDetails user) {
        List<EmployeeAll> result = new ArrayList<>();
        boolean hasAnySchedule = false;

        // 로그인한 사용자 정보 먼저 가져오기
        Employee loggedInEmployee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        List<EmployeeHalfLeaveResponse> loggedInHalfLeave = holidayRepository.getHolidayDay(loggedInEmployee.getEmployeeNo(), data);
        List<EmployeeSchedulesResponse> loggedInSchedules = scheduleRepository.getScheduleDay(loggedInEmployee.getEmployeeNo(), data);
        List<EmployeeAnnualLeaveResponse> loggedInAnnualLeave = holidayRepository.getAnnualDay(loggedInEmployee.getEmployeeNo(), data);

        Commute commute = commuteRepository.findTopByEmployeeOrderByCommuteDateDesc(loggedInEmployee)
                .orElseThrow();

        String commuteStartTime = formatTime(commute.getCommuteStart());
        String commuteEndTime = formatTime(commute.getCommuteEnd());
        CommutePlace commutePlace = commute.getCommutePlace();

        boolean hasHalfLeave = !loggedInHalfLeave.isEmpty();
        boolean hasAnnualLeave = !loggedInAnnualLeave.isEmpty();
        boolean hasSchedules = !loggedInSchedules.isEmpty();

        EmployeeBooleanResponse booleanResponse = new EmployeeBooleanResponse(hasHalfLeave, hasAnnualLeave, hasSchedules);

        EmployeeAll loggedInEmployeeAll = new EmployeeAll(loggedInEmployee.getEmployeeNo(), loggedInEmployee.getName(),
                loggedInEmployee.getPosition(), null, commuteStartTime, commuteEndTime, commutePlace,
                booleanResponse, loggedInSchedules, loggedInHalfLeave, loggedInAnnualLeave);

        result.add(loggedInEmployeeAll); // 로그인한 사용자 정보 먼저 추가

        // 모든 직원 정보 가져오기
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            if (employee.getEmployeeNo().equals(loggedInEmployee.getEmployeeNo())) {
                // 로그인한 사용자는 건너뛰기
                continue;
            }

            Long employeeId = employee.getEmployeeNo();

            List<EmployeeHalfLeaveResponse> halfLeave = holidayRepository.getHolidayDay(employeeId, data);
            List<EmployeeSchedulesResponse> schedules = scheduleRepository.getScheduleDay(employeeId, data);
            List<EmployeeAnnualLeaveResponse> annualLeave = holidayRepository.getAnnualDay(employeeId, data);

            hasHalfLeave = !halfLeave.isEmpty();
            hasAnnualLeave = !annualLeave.isEmpty();
            hasSchedules = !schedules.isEmpty();

            EmployeeBooleanResponse booleanResponse2 = new EmployeeBooleanResponse(hasHalfLeave, hasAnnualLeave, hasSchedules);

            EmployeeAll employeeAll = new EmployeeAll(employeeId, employee.getName(), employee.getPosition(), null,
                    commuteStartTime, commuteEndTime, commutePlace, booleanResponse2, schedules, halfLeave, annualLeave);

            result.add(employeeAll);
            hasAnySchedule = hasAnySchedule || hasHalfLeave || hasAnnualLeave || hasSchedules;
        }

        if (!hasAnySchedule) {
            // 아무도 일정이 없을 경우, 로그인한 사용자의 정보만 반환
            result.clear();
            result.add(loggedInEmployeeAll);
        }

        return result;
    }


    //직원 일정 추가
    public EmployeeAddScheduleRequestDTO addEmployee(CustomUserDetails user, EmployeeAddScheduleRequestDTO request) {

            Schedule schedule = new Schedule();

            Employee employee = employeeRepository.findByEmployeeId(user.getUsername())
                    .orElse(null);

            if (employee == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
            }

            Company company = companyRepository.findById(employee.getCompany().getCompanyNo())
                    .orElse(null);
            if (company == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
            }

            schedule.addEmployee(employee, company, request.startTime(), request.endTime(), request.title());
            scheduleRepository.save(schedule);

            return new EmployeeAddScheduleRequestDTO(schedule.getScheduleNo(), request.startTime(),request.endTime(), request.title());

    }


    //직원 스케줄 수정
    @Transactional
    public UpdateComScheduleDto updateSchedule(UpdateComScheduleDto request, CustomUserDetails user) {

        Employee employee = employeeRepository.findByEmployeeNo(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        Schedule schedule = scheduleRepository.findById(request.ScheduleId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE));

        log.info("employee.getEmployeeNo() = {}, schedule.getEmployee().getEmployeeNo()= {}", employee.getEmployeeNo(), schedule.getEmployee().getEmployeeNo());

        if (Objects.equals(employee.getEmployeeNo(), schedule.getEmployee().getEmployeeNo())) {
            schedule.update(request.title(), request.startDate(), request.endDate());
            scheduleRepository.save(schedule);
        } else {
            throw new BusinessException(ErrorCode.NOT_EQUAL_MERCHANT_ID);
        }
        return request;
    }

    // 직원 일정 삭제
    @Transactional
    public String deleteSchedule(CustomUserDetails user, Long scheduleId) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        // 해당 Schedule이 현재 로그인한 사용자의 것인지 확인
        if (schedule.getEmployee().equals(employee)) {
            // Employee 엔티티에서 Schedule 엔티티 제거
            employee.getEmployeeScheduleList().remove(schedule);
            // Schedule 삭제
            scheduleRepository.deleteById(scheduleId);
            return "삭제 완료. 삭제된 Schedule ID: " + scheduleId;
        } else {
            return "삭제 실패. 해당 Schedule은 현재 로그인한 사용자의 것이 아닙니다.";
        }
    }

    //시간 포맷
    private String formatTime(LocalTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
