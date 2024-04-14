package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.company.CompanyScheduleResponseDto;
import com.github.riset_backend.schedules.dto.company.CompanyUpdateDateTimeDto;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.dto.company.CompanyScheduleRequestDto;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanySchedulesService {
    private static final Logger log = LoggerFactory.getLogger(CompanySchedulesService.class);
    private final ScheduleRepository schedulesRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    // 회사의 해당하는 월의 값을 받아서 전부 return / 회사일정 시 달력의 값에 넣어주기 위해서
    public List<CompanyScheduleResponseDto> getAllCompanySchedules(String total, CustomUserDetails user) {
        // 회사정보
        Company company = companyRepository.findById(user.getEmployee().getCompany().getCompanyNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

        String year = total.substring(0, 4);
        String currentMonth = total.substring(4, 6);

        Function<Schedule, CompanyScheduleResponseDto> mapToDto = this::mapScheduleToDto;

        return company.getCompanySchedules().stream()
                .filter(schedule -> String.valueOf(schedule.getStartDate().getYear()).equals(year))
                .filter(schedule -> String.valueOf(schedule.getStartDate().getMonthValue()).equals(currentMonth))
                .sorted(Comparator.comparing(Schedule::getStartDate))
                .map(mapToDto)
                .collect(Collectors.toList());
    }

    // 회사 일정 추가
    @Transactional
    public void schedulesAdd(CompanyScheduleRequestDto request, CustomUserDetails user) {

        Optional<Employee> employeeUser = employeeRepository.findByEmployeeId(user.getUsername());

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;


        try {
            // startDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.startTime().contains("T")) {
                startDateTime = LocalDateTime.parse(request.startTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                startDateTime = LocalDateTime.parse(request.startTime() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            // endDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.endTime().contains("T")) {
                endDateTime = LocalDateTime.parse(request.endTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                endDateTime = LocalDateTime.parse(request.endTime() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }


        if (employeeUser.isPresent()) {
            Company company = companyRepository.findById(employeeUser.get().getCompany().getCompanyNo())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));
            Schedule schedule = Schedule.builder()
                    .employee(employeeUser)
                    .company(company)
                    .startDate(startDateTime)
                    .endDate(endDateTime)
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
    public Schedule updateComSchedule(CompanyUpdateDateTimeDto request) {

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        try {
            // startDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.startTime().contains("T")) {
                startDateTime = LocalDateTime.parse(request.startTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                startDateTime = LocalDateTime.parse(request.startTime() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            // endDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.endTime().contains("T")) {
                endDateTime = LocalDateTime.parse(request.endTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                endDateTime = LocalDateTime.parse(request.endTime() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }

        // DTO의 String 타입 필드들을 Map으로 추출
        Map<String, String> fieldValues = extractStringFields(request);
        // Custom Repository 메서드를 호출하여 스케줄 동적 업데이트 실행
        schedulesRepository.updateScheduleDynamic(request.scheduleNo(), fieldValues, startDateTime, endDateTime);

        // 업데이트된 스케줄을 조회하여 반환, 없으면 BusinessException 발생
        return schedulesRepository.findById(request.scheduleNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EQUAL_MERCHANT_ID, "해당 스케줄이 존재하지 않습니다."));

    }


    //삭제
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


    //String 추출 대입
    private Map<String, String> extractStringFields(CompanyUpdateDateTimeDto dto) {
        return Stream.of(
                        getFieldEntry("title", dto.title()),
                        getFieldEntry("content", dto.content()),
                        getFieldEntry("writer", dto.writer()),
                        getFieldEntry("color", dto.color())
                )
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //map 엔트리 생성
    private Map.Entry<String, String> getFieldEntry(String fieldName, String value) {
        return new AbstractMap.SimpleEntry<>(fieldName, value);
    }

    //전체 조회할 때 00:00 일 경우 삭제
    private CompanyScheduleResponseDto mapScheduleToDto(Schedule schedule) {

        String startDateString = schedule.getStartDate().toString();
        if (startDateString.contains("T") && startDateString.endsWith("T00:00")) {
            startDateString = startDateString.substring(0, startDateString.indexOf("T"));
        }
        String endDateString = schedule.getEndDate().toString();
        if (endDateString.contains("T") && endDateString.endsWith("T00:00")) {
            endDateString = endDateString.substring(0, endDateString.indexOf("T"));
        }


        return new CompanyScheduleResponseDto(
                schedule.getScheduleNo(),
                schedule.getWriter(),
                schedule.getTitle(),
                schedule.getContent(),
                startDateString,
                endDateString,
                schedule.getColor()
        );
    }
}