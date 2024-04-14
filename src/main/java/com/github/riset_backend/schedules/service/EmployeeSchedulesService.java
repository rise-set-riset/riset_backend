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
import com.github.riset_backend.vacations.dto.Status;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public List<EmployeeAll> getAllEmployees(CustomUserDetails user, LocalDate data) {

        //data 에 해당하는 일정, 시간순
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        List<EmployeeHalfLeaveResponse> halfLeave = holidayRepository.getHolidayDay(employee.getEmployeeNo(), data);
        List<EmployeeSchedulesResponse> schedules = scheduleRepository.getScheduleDay(employee.getEmployeeNo(), data);
        List<EmployeeAnnualLeaveResponse> annualLeave = holidayRepository.getAnnualDay(employee.getEmployeeNo(), data);

        //출퇴근 시간 가져오기
        Commute commute = commuteRepository.findById(employee.getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.SERVER_ERROR));

        //출근시간
        String commuteStartTime = formatTime(commute.getCommuteStart());
        //퇴근시간
        String commuteEndTime = formatTime(commute.getCommuteEnd());
        //본부, 재택, 외근
        CommutePlace commutePlace = commute.getCommutePlace();


        // EmployeeBooleanResponse 값 초기화
        boolean hasHalfLeave = (halfLeave != null && !halfLeave.isEmpty());
        boolean hasAnnualLeave = (annualLeave != null && !annualLeave.isEmpty());
        boolean hasSchedules = (schedules != null && !schedules.isEmpty());

        // EmployeeBooleanResponse 객체를 생성합니다.
        EmployeeBooleanResponse booleanResponse = new EmployeeBooleanResponse(hasHalfLeave, hasAnnualLeave, hasSchedules);

        EmployeeAll combinedResponse = new EmployeeAll(employee.getEmployeeNo(), employee.getName(),
                employee.getDepartment().getDeptName(), employee.getPosition(),employee.getMyImage().getFilePath(),
                commuteStartTime, commuteEndTime, commutePlace, booleanResponse, schedules, halfLeave, annualLeave);


        return Collections.singletonList(combinedResponse);
    }


    //직원 일정 추가
    public EmployeeAddScheduleResponseDTO addEmployee(CustomUserDetails user, EmployeeAddScheduleRequestDTO request) {
        Schedule schedule = new Schedule();

        Employee employee = employeeRepository.findByEmployeeId(user.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE));

        Company company = companyRepository.findById(employee.getCompany().getCompanyNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        schedule.addEmployee(employee, company, request.startTime(), request.endTime(), request.title());

        scheduleRepository.save(schedule);

        return new EmployeeAddScheduleResponseDTO(
                schedule.getScheduleNo(),
                request.startTime(),
                request.endTime(),
                request.title());
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

    //시간 포맷
    private String formatTime(LocalTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
