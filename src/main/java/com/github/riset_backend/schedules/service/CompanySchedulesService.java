package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.company.*;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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


    // 회사정보 조회
    public List<CompanyScheduleResponseDto> getAllCompanySchedules(String total, CustomUserDetails user) {

        Company company = companyRepository.findById(user.getEmployee().getCompany().getCompanyNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

        int year = Integer.parseInt(total.substring(0, 4));
        String currentMonth = total.substring(4, 6).replaceFirst("^0*", "");

        return company.getCompanySchedules().stream()
                .filter(schedule -> String.valueOf(schedule.getStartDate().getYear()).equals(String.valueOf(year)))
                .filter(schedule -> String.valueOf(schedule.getStartDate().getMonthValue()).equals(currentMonth))
                .map(schedule -> new CompanyScheduleResponseDto(
                        schedule.getScheduleNo(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        String.valueOf(schedule.getStartDate()),
                        String.valueOf(schedule.getEndDate()),
                        schedule.getColor()
                ))
                .collect(Collectors.toList());
    }

    //회사 일정 추가
    @Transactional
    public CompanyScheduleRequestDto schedulesAdd(CompanyScheduleRequestDto request, CustomUserDetails user) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));


        log.info(String.valueOf(employee.getEmployeeNo()));

        Schedule schedule = new Schedule();
        if (!request.start().contains("T") && request.end().contains("T")) {
            // 시작일이 없고 종료일에 시간 정보가 포함되어 있으면
            // 시작일에는 T00:00을 붙이고, 종료일의 시간을 23:59로 변경하여 저장
            schedule.addTime(request.start() + "T00:00", request.end().replace("T24:00", "T23:59"), request.title(), request.writer(), request.content(), request.color(), employee);
        }
        if (request.start().contains("T") && request.end().contains("T")) {
            // 시작일과 종료일이 모두 시간 정보를 포함하고, 일자가 같은 경우
            if (request.start().substring(0, 10).equals(request.end().substring(0, 10))) {
                schedule.addTime(request.start(), request.end(), request.title(), request.writer(), request.content(), request.color(), employee);
            } else {
                // 시작일과 종료일이 모두 시간 정보를 포함하지만, 일자가 다른 경우
                // 종료일의 시간을 23:59로 변경하여 저장
                schedule.addTime(request.start(), request.end().replace("T24:00", "T23:59"), request.title(), request.writer(), request.content(), request.color(), employee);
            }
        } else if (!request.start().contains("T") && !request.end().contains("T")) {
            schedule.addTime(request.start() + "T00:00", request.end() + "T23:59", request.title(), request.writer(), request.content(), request.color(), employee);
        } else if (!request.start().contains("T") && !request.end().contains("T") && request.start().equals(request.end())) {
            schedule.addTime(request.start(), request.end(), request.title(), request.writer(), request.content(), request.color(), employee);
        } else if (request.start().substring(0, 10).equals(request.end().substring(0, 10)) && request.start().contains("T")) {
            schedule.addTime(request.start(), request.end(), request.title(), request.writer(), request.content(), request.color(),employee);
        }

        String startDateString = schedule.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String endDateString = schedule.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

// 시작일이 T00:00:00일 경우 시간 정보를 잘라냄
        if (schedule.getStartDate().toLocalTime().equals(LocalTime.MIN)) {
            startDateString = schedule.getStartDate().toLocalDate().toString();
        }

// 종료일이 T23:59:00일 경우 시간 정보를 T24:00으로 변경
        if (schedule.getEndDate().toLocalTime().equals(LocalTime.of(23, 59))) {
            endDateString = schedule.getEndDate().toLocalDate().plusDays(1).toString();
        }

        Long save = schedulesRepository.save(schedule).getScheduleNo();
// 변경된 시작일과 종료일을 CompanyScheduleRequestDto로 반환
        return new CompanyScheduleRequestDto(
                save,
                startDateString,
                endDateString,
                schedule.getTitle(),
                schedule.getWriter(),
                schedule.getContent(),
                schedule.getColor()
        );
    }


    //수정하기
    @Transactional
    public Schedule updateComSchedule(CompanyUpdateDateTimeDto request) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        try {
            // startDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.start().contains("T")) {
                start = LocalDateTime.parse(request.start(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                start = LocalDateTime.parse(request.start() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            // endDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.end().contains("T")) {
                end = LocalDateTime.parse(request.end(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                end = LocalDateTime.parse(request.end() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }

        // DTO의 String 타입 필드들을 Map으로 추출
        Map<String, String> fieldValues = extractStringFields(request);
        // Custom Repository 메서드를 호출하여 스케줄 동적 업데이트 실행
        schedulesRepository.updateScheduleDynamic(request.scheduleNo(), fieldValues, start, end);

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


    // String 추출 대입
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

    // map 엔트리 생성
    private Map.Entry<String, String> getFieldEntry(String fieldName, String value) {
        return new AbstractMap.SimpleEntry<>(fieldName, value);
    }


}