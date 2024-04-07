package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.employee.EmployeeDTO;
import com.github.riset_backend.schedules.dto.employee.HolidaysDTO;
import com.github.riset_backend.schedules.dto.employee.ScheduleDTO;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.entity.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeSchedulesService {

    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;


    // 첫 진입 조회
    public List<EmployeeDTO> getAllEmployees(CustomUserDetails user) {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO convertToDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getEmployeeNo());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());

        // Employee가 가진 모든 Holiday의 leaveStatus 가져오기
        List<LeaveType> leaveStatuses = employee.getHolidays().stream()
                .map(Holiday::getLeaveStatus)
                .toList();

        if (!leaveStatuses.isEmpty()) {
            LeaveType recentLeaveStatus = leaveStatuses.get(leaveStatuses.size() - 1);
            dto.setOnLeave(mapLeaveStatusToBoolean(recentLeaveStatus));
        }

        dto.setImage("image"); // 이미지는 어떻게?
        dto.setStatus("근무중"); //enum 값 확인 -> 근무중, 회의중, 진행중, 진행예정


        List<HolidaysDTO> halfDay = employee.getHolidays().stream()
                .filter(holiday -> holiday.getHalfDay() != null && holiday.getHalfDay())
                .map(holiday -> {
                    HolidaysDTO request = new HolidaysDTO();
                    request.setHolidayNo(holiday.getLeaveNo());
                    request.setStartDate(holiday.getStartDate());
                    request.setEndDate(holiday.getEndDate());
                    request.setContent(holiday.getComment());
                    request.setHalfStatus(mapLeaveStatusToBoolean(holiday.getLeaveStatus())); //대기중 값 설정할 예정
                    return request;
                })
                .collect(Collectors.toList());
        dto.setHalfDayLeave(halfDay);


//        employee.getHolidays().get().addAll();

        //일정
        if (employee.getEmployeeNo() == 1) {
            dto.setEditable(true);
            dto.setScheduleDTOs(employee.getEmployeeScheduleList().stream()
                    .map(schedule -> new ScheduleDTO(schedule.getScheduleNo(), schedule.getStartDate(), schedule.getEndDate(), schedule.getContent(), schedule.getStatus()))
                    .collect(Collectors.toList()));
        }


        return dto;
    }

    //직원 일정 추가
    public ScheduleDTO addEmployee(CustomUserDetails user, ScheduleDTO request) {
        Schedule schedule = new Schedule();
        Employee employee = employeeRepository.findByEmployeeId(user.getUsername()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE));
        schedule.addEmployee(employee, request.getStartDate(), request.getEndDate(), request.getContent(), request.getStatus());

        scheduleRepository.save(schedule);
        return request;
    }

    public Boolean mapLeaveStatusToBoolean(LeaveType leaveStatus) {
        if (leaveStatus == LeaveType.HALF_DAY_LEAVE) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
