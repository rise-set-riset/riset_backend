package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.company.CompanyScheduleResponseDto;
import com.github.riset_backend.schedules.dto.company.UpdateComScheduleDto;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.dto.company.CompanyScheduleRequestDto;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanySchedulesService {
    private final ScheduleRepository schedulesRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;


    // 회사 일정 추가
    @Transactional
    public void schedulesAdd(CompanyScheduleRequestDto request, CustomUserDetails user) {


        Optional<Employee> employeeUser = employeeRepository.findByEmployeeId(user.getUsername());

        if (employeeUser.isPresent()) {
            Company company = companyRepository.findById(employeeUser.get().getCompany().getCompanyNo())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));
            Schedule schedule = Schedule.builder()
                    .employee(employeeUser)
                    .company(company)
                    .startDate(request.startDate())
                    .endDate(request.endDate())
                    .content(request.content())
                    .title(request.title())
                    .writer(request.writer())
                    .color(request.color())
                    .build();

            company.getCompanySchedules().add(schedule);
            companyRepository.save(company);
        } else {
            throw new BusinessException(ErrorCode.NOT_ADMIN, "직원 정보를 찾을 수 없습니다.");
        }


    }

    //수정하기
    @Transactional
    public Schedule updateComSchedule(UpdateComScheduleDto request) {
        //todo : token 유효성 확인 해야합니다
        //todo : 에러 상태코드 수정 해야합니다
        Schedule schedule = schedulesRepository.findById(request.ScheduleId()).orElseThrow(() -> new BusinessException(ErrorCode.NO_BUY_ORDER, "없습니다"));

        schedule.update(
                request.title(),
                request.content(),
                request.startDate(),
                request.endDate(),
                request.writer(),
                request.color()
        );


        Schedule saveSchedule = schedulesRepository.save(schedule);


        return saveSchedule;
    }


    // 회사의 해당하는 월의 값을 받아서 전부 return / 회사일정 시 달력의 값에 넣어주기 위해서
    public Map<String, List<CompanyScheduleResponseDto>> getAllCompanySchedules(String currentMonth, CustomUserDetails user) {

        //회사정보
        Company company = companyRepository.findById(user.getEmployee().getCompany().getCompanyNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

        return company.getCompanySchedules().stream()
                .filter(schedule -> schedule.getStartDate().getMonthValue() == Long.parseLong(currentMonth))
                .sorted(Comparator.comparing(Schedule::getStartDate))
                .map(schedule -> new CompanyScheduleResponseDto(
                        schedule.getScheduleNo(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getStartDate().toString(),
                        schedule.getEndDate().toString(),
                        schedule.getColor()
                ))
                .collect(Collectors.groupingBy(dto -> String.format("%02d일", LocalDateTime.parse(dto.startDate()).getDayOfMonth()), TreeMap::new, Collectors.toList()));
    }

    //회사 일정 수정
    @Transactional
    public String deleteCompanySchedule(Long scheduleId, CustomUserDetails user) {
        Optional<Schedule> scheduleOptional = schedulesRepository.findById(scheduleId);

        //유저 확인, 삭제권한이기 때문에 어드민 role 확인 해야함,
        employeeRepository.findByEmployeeId(user.getUsername()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER, "유저가 없습니다."));

        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            schedulesRepository.delete(schedule);
            return "삭제되었습니다"; // 삭제 성공 메시지 반환
        } else {
            throw new NoSuchElementException("삭제할 일정을 찾을 수 없습니다"); // 일정이 존재하지 않을 때 예외 던지기
        }
    }
}