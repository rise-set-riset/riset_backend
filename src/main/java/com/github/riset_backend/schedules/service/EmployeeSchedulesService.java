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
import com.github.riset_backend.vacations.dto.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeSchedulesService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeSchedulesService.class);
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final CompanyRepository companyRepository;

    // 첫 진입 조회
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees(UserDetails user, LocalDate data) {
        List<Employee> employees = employeeRepository.findAll();

        int month = data.getMonth().getValue();
        int day = data.getDayOfMonth();

        log.info("month={}, day={}", month, day);

        // month, day 필터
        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> e.getEmployeeScheduleList().stream()
                        .anyMatch(s -> s.getStartDate().getMonth().getValue() == month && s.getStartDate().getDayOfMonth() == day))
                .toList();

        return filteredEmployees.stream()
                .map(e -> new EmployeeDTO(
                        e.getEmployeeNo(),
                        e.getName(),
                        e.getDepartment().getDeptName(),
                        e.getPosition(),
                        e.getMyImage().getFilePath()
                )).toList();
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

        return new EmployeeAddScheduleResponseDTO(
                schedule.getScheduleNo(),
                request.startDateTime(),
                request.endDateTIme(),
                request.content(),
                request.title(),
                schedule.getStatus());
    }

    //직원 스케줄 수정
    @Transactional
    public UpdateComScheduleDto updateSchedule(UpdateComScheduleDto request, CustomUserDetails user) {

        Employee employee = employeeRepository.findByEmployeeNo(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        Schedule schedule = scheduleRepository.findById(request.ScheduleId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE));

        log.info("employee.getEmployeeNo() = {}, schedule.getEmployee().getEmployeeNo()= {}", employee.getEmployeeNo(), schedule.getEmployee().getEmployeeNo());

        if (Objects.equals(employee.getEmployeeNo(), schedule.getEmployee().getEmployeeNo())) {
            schedule.update(request.title(), request.content(), request.startDate(), request.endDate(), schedule.getWriter());
            scheduleRepository.save(schedule);
        } else {
            throw new BusinessException(ErrorCode.NOT_EQUAL_MERCHANT_ID);
        }


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
