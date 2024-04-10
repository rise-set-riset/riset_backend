package com.github.riset_backend.login.commute.service;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.commute.dto.CommuteDto;
import com.github.riset_backend.login.commute.dto.CommuteResponseDto;
import com.github.riset_backend.login.commute.entity.Commute;
import com.github.riset_backend.login.commute.entity.CommutePlace;
import com.github.riset_backend.login.commute.repository.CommuteRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommuteService {
    private final CommuteRepository commuteRepository;
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;

    public ResponseEntity<?> addCommute(CommuteDto commuteDto) {
        log.info("dto: {}", commuteDto.toString());
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(commuteDto.name()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER)));

        Commute commute = Commute.builder()
                .commuteDate(commuteDto.commuteDate())
                .employee(employee.get())
                .commuteStart(commuteDto.commuteStart())
                .commuteEnd(commuteDto.commuteEnd())
                .commutePlace(CommutePlace.valueOf(commuteDto.commutePlace()))
                .build();
        commuteRepository.save(commute);

        return ResponseEntity.ok().body("추가되었습니다.");
    }

    public List<CommuteResponseDto> getCommuteHistory(CustomUserDetails customUserDetails) {
        //ToDo: 본사와 지사의 위도, 경도 값을 넘겨줘야 함.
        Employee employee = employeeRepository.findById(customUserDetails.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        LocalDate localDate = LocalDate.now();

        int year = localDate.getYear();
        int month = localDate.getMonthValue();

        List<Holiday> holidays = holidayRepository.findByYearAndMonthAndEmployee(year, month, employee);
        List<Commute> commute = commuteRepository.findByYearAndMonthAndEmployee(year, month, employee);


        List<Commute> halfDayLeaves = new ArrayList<>();
        List<Commute> fullDayWorks = new ArrayList<>();
        List<Holiday> fullLeaves = new ArrayList<>();


        for (Commute record : commute) {
            long hoursWorked = Duration.between(record.getCommuteStart(), record.getCommuteEnd()).toHours();
            if (hoursWorked >= 5) { // 본사, 재택, 외근 (풀근무)
                fullDayWorks.add(record);
            } else { //본사, 재택, 외근 (반차)
                halfDayLeaves.add(record);
            }
        }


        for (Holiday fullLeave : holidays) { // 연차
            if (fullLeave.getLeaveStatus().ordinal() == LeaveType.ANNUAL_LEAVE.ordinal()) {
                fullLeaves.add(fullLeave);
            }
        }


        List<CommuteResponseDto> commuteResponseDtos = new ArrayList<>();


        List<CommuteResponseDto> fullDayWorksDtos = fullDayWorks.stream()
                .map(record -> CommuteResponseDto.builder()
                        .employeeName(record.getEmployee().getName())
                        .date(record.getCommuteDate().toString())
                        .commuteType("풀근무")
                        .build())
                .toList();
        commuteResponseDtos.addAll(fullDayWorksDtos);


        List<CommuteResponseDto> halfDayLeavesDtos = halfDayLeaves.stream()
                .map(record -> CommuteResponseDto.builder()
                        .employeeName(record.getEmployee().getName())
                        .date(record.getCommuteDate().toString())
                        .commuteType("반차")
                        .build())
                .toList();
        commuteResponseDtos.addAll(halfDayLeavesDtos);


        List<CommuteResponseDto> fullLeavesDtos = fullLeaves.stream()
                .map(leave -> CommuteResponseDto.builder()
                        .employeeName(leave.getEmployee().getName())
                        .date(leave.getStartDate().toString())
                        .commuteType("연차")
                        .build())
                .toList();
        commuteResponseDtos.addAll(fullLeavesDtos);

        return commuteResponseDtos;
    }
}
