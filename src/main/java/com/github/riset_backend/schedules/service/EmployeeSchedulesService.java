package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.company.UpdateComScheduleDto;
import com.github.riset_backend.schedules.dto.employee.*;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.dto.Status;
import com.github.riset_backend.vacations.entity.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeSchedulesService {

    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final CompanyRepository companyRepository;

    // 첫 진입 조회
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees(CustomUserDetails user, LocalDate target) {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            EmployeeDTO dto = convertToDTO(employee, i == 0, target); // 첫 번째 직원 여부 전달
            employeeDTOs.add(dto);
        }

        return employeeDTOs;
    }

    private EmployeeDTO convertToDTO(Employee employee, boolean isFirstEmployee, LocalDate target) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getEmployeeNo());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setImage("image"); // 이미지는 어떻게?
        dto.setStatus("근무중"); //enum 값 확인 -> 근무중, 회의중, 진행중, 진행예정

        //연차 반차 타입
        List<LeaveType> leaveStatuses = employee.getHolidays().stream()
                .map(Holiday::getLeaveStatus)
                .toList();
        if (!leaveStatuses.isEmpty()) {
            LeaveType recentLeaveStatus = leaveStatuses.get(leaveStatuses.size() - 1);
            if (recentLeaveStatus == LeaveType.ANNUAL_LEAVE) {
                dto.setIsAnnualLeave(true);
                dto.setIsHalfDayLeave(false);
            } else if (recentLeaveStatus == LeaveType.HALF_DAY_LEAVE) {
                dto.setIsAnnualLeave(false);
                dto.setIsHalfDayLeave(true);
            }
        } else {
            dto.setIsAnnualLeave(false);
            dto.setIsHalfDayLeave(false);
        }


        if (isFirstEmployee) {
            // 첫 번째 직원에 대한 처리
            dto.setEditable(true);
            dto.setScheduleDTOs(employee.getEmployeeScheduleList().stream()
                    .filter(schedule -> schedule.getStartDate().toLocalDate().equals(target))
                    .map(schedule -> new ScheduleDTO(schedule.getScheduleNo(), schedule.getStartDate(), schedule.getEndDate(), schedule.getContent(), schedule.getStatus()))
                    .collect(Collectors.toList()));

            if (dto.getIsAnnualLeave()) {
                List<AnnualLeaveDTO> annualLeaveList = employee.getHolidays().stream()
                        .filter(holiday -> holiday.getLeaveStatus() == LeaveType.ANNUAL_LEAVE && holiday.getStartDate().toLocalDate().equals(target))
                        .map(holiday -> new AnnualLeaveDTO(
                                holiday.getLeaveNo(),
                                holiday.getStartDate(),
                                holiday.getComment(),
                                Status.PENDING
                        ))
                        .toList();
                dto.setAnnualLeave(annualLeaveList);
            }

            if (dto.getIsHalfDayLeave()) {
                List<HalfDayLeaveDTO> halfDayLeaveList = employee.getHolidays().stream()
                        .filter(holiday -> holiday.getLeaveStatus() == LeaveType.HALF_DAY_LEAVE && holiday.getStartDate().toLocalDate().equals(target))
                        .map(holiday -> new HalfDayLeaveDTO(
                                holiday.getLeaveNo(),
                                holiday.getStartDate(),
                                holiday.getEndDate(),
                                holiday.getComment(),
                                Status.PENDING
                        ))
                        .toList();
                dto.setHalfDayLeave(halfDayLeaveList);
            }
        }

        return dto;
    }

    //직원 일정 추가
    public EmployeeAddScheduleResponseDTO addEmployee(CustomUserDetails user, EmployeeAddScheduleRequestDTO request) {
        Schedule schedule = new Schedule();
        Employee employee = employeeRepository.findByEmployeeId(user.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE));
        Company company = companyRepository.findById(employee.getCompany().getCompanyNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        schedule.addEmployee(employee, company, request.startDateTime(), request.endDateTIme(), request.content(), request.title(), Status.PENDING);

        scheduleRepository.save(schedule);

        EmployeeAddScheduleResponseDTO response = new EmployeeAddScheduleResponseDTO(employee.getEmployeeNo(),
                company.getCompanyNo(),
                request.startDateTime(),
                request.endDateTIme(),
                request.content(),
                request.title(),
                schedule.getStatus());
        return response;
    }

    //직원 스케줄 수정
    public UpdateComScheduleDto updateSchedule(UpdateComScheduleDto request, CustomUserDetails user) {
        Employee employee = employeeRepository.findByEmployeeNo(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        Schedule schedule = scheduleRepository.findById(request.ScheduleId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE));
        if (Objects.equals(employee.getEmployeeNo(), schedule.getEmployee().getEmployeeNo())) {
            schedule.update(request.title(), request.content(), request.startDate(), request.endDate(), schedule.getWriter(), request.color());
        } else {
            throw new BusinessException(ErrorCode.NOT_EQUAL_MERCHANT_ID);
        }
        scheduleRepository.save(schedule);

        return request;
    }

    // 직원 일정 삭제
    public String deleteSchedule(CustomUserDetails user, Long scheduleId) {

        Employee employee = employeeRepository.findByEmployeeNo(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_BUY_ITEM));

        if (Objects.equals(employee.getEmployeeNo(), schedule.getEmployee().getEmployeeNo())) {
            scheduleRepository.deleteById(scheduleId);
            return "삭제 되었습니다";
        } else {
            throw new BusinessException(ErrorCode.NOT_EQUAL_MERCHANT_ID);
        }
    }


}
