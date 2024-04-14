package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.schedules.dto.company.CompanyResponseDto;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public List<CompanyScheduleResponseDto> getAllCompanySchedules(String total, CustomUserDetails user) {
        // 회사정보
        Company company = companyRepository.findById(user.getEmployee().getCompany().getCompanyNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

        String yearString = total.substring(0, 4);
        String currentMonth = total.substring(4, 6).replaceFirst("^0*", "");

        return company.getCompanySchedules().stream()
                .filter(schedule -> String.valueOf(schedule.getStartDate().getYear()).equals(yearString))
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
    //회사 추가
    @Transactional
    public CompanyResponseDto schedulesAdd(CompanyScheduleRequestDto request, CustomUserDetails user) {
        Optional<Employee> employeeUser = employeeRepository.findByEmployeeId(user.getUsername());

        if (employeeUser.isPresent()) {
            LocalDateTime startDateTime;
            LocalDateTime endDateTime;

            try {
                // 시작 시간 및 종료 시간을 파싱합니다.
                startDateTime = parseDateTime(request.start());
                endDateTime = parseDateTime(request.end());
            } catch (DateTimeParseException e) {
                // 날짜 및 시간 형식이 올바르지 않은 경우 적절한 예외를 던집니다.
                throw new BusinessException(ErrorCode.EXIST_FAVORITE, "날짜 및 시간 형식이 올바르지 않습니다.");
            }

            Company company = employeeUser.get().getCompany();

            // 스케줄 객체 생성
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

            // 스케줄 저장 및 ID 반환
            Schedule savedSchedule = schedulesRepository.save(schedule);
            System.out.println("Saved scheduleNo: " + savedSchedule.getScheduleNo());
            // 스케줄 번호도 함께 반환합니다.
            return new CompanyResponseDto(
                    savedSchedule.getScheduleNo(), // 스케줄 번호
                    savedSchedule.getStartDate(), // 시작 날짜
                    savedSchedule.getEndDate(), // 끝나는 날짜
                    savedSchedule.getTitle(), // 일정 제목
                    savedSchedule.getWriter(), // 작성자
                    savedSchedule.getContent(), // 내용
                    savedSchedule.getColor() // 색상
            );

        } else {
            // 유저가 존재하지 않으면 적절한 예외를 던집니다.
            throw new BusinessException(ErrorCode.EXPIRED_JWT_ERROR, "유저 정보를 찾을 수 없습니다.");
        }
    }


    private LocalDateTime parseDateTime(String dateTimeString) {
        // 문자열을 T를 기준으로 날짜와 시간으로 분리합니다.
        String[] dateTimeParts = dateTimeString.split("T");

        // 날짜 부분과 시간 부분을 추출합니다.
        String dateString = dateTimeParts[0];
        String timeString = dateTimeParts.length > 1 ? dateTimeParts[1] : "00:00"; // 시간 부분이 없으면 "00:00"을 기본값으로 설정합니다.

        // 날짜와 시간을 파싱하여 LocalDateTime으로 변환합니다.
        LocalDate date = LocalDate.parse(dateString);
        LocalTime time = LocalTime.parse(timeString);


        // LocalDateTime 객체를 생성합니다.
        return LocalDateTime.of(date, time);
    }


    //수정하기
    @Transactional
    public Schedule updateComSchedule(CompanyUpdateDateTimeDto request) {

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        try {
            // startDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.start().contains("T")) {
                startDateTime = LocalDateTime.parse(request.start(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                startDateTime = LocalDateTime.parse(request.start() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            // endDate가 T를 포함하는지 확인하여 처리합니다.
            if (request.end().contains("T")) {
                endDateTime = LocalDateTime.parse(request.end(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                endDateTime = LocalDateTime.parse(request.end() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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


}