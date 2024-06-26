package com.github.riset_backend.login.commute.service;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.commute.dto.*;
import com.github.riset_backend.login.commute.entity.Commute;
import com.github.riset_backend.login.commute.entity.CommutePlace;
import com.github.riset_backend.login.commute.entity.CommuteStatus;
import com.github.riset_backend.login.commute.repository.CommuteRepository;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommuteService {
    private final CommuteRepository commuteRepository;
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;
    private final CompanyRepository companyRepository;

    public ResponseEntity<?> registerCommute(CommuteDto commuteDto, CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        Commute commute = Commute.builder()
                .commuteDate(commuteDto.commuteDate())
                .employee(employee)
                .commuteStart(commuteDto.commuteStart())
                .commutePlace(CommutePlace.valueOf(commuteDto.commutePlace()))
                .commuteStatus(CommuteStatus.valueOf(commuteDto.commuteStatus()))
                .build();

        commuteRepository.save(commute);

        return ResponseEntity.ok().body("half");
    }

    public ResponseEntity<?> addCommute(CustomUserDetails customUserDetails, CommuteRequestDto commuteRequestDto) {
        LocalDate localDate = LocalDate.now();

        Employee employee = findById(customUserDetails);

        Optional<Commute> comOptional = commuteRepository.findByEmployeeAndCommuteDate(employee, localDate);

        if (comOptional.isPresent()) {
            Commute com = comOptional.get();
            com.setCommuteDate(commuteRequestDto.commuteDate());
            com.setCommuteStart(commuteRequestDto.commuteStart());
            com.setCommuteEnd(commuteRequestDto.commuteEnd());
            com.setCommutePlace(CommutePlace.valueOf(commuteRequestDto.commutePlace()));
            com.setCommuteStatus(CommuteStatus.valueOf(commuteRequestDto.commuteStatus()));
            commuteRepository.save(com);
        } else {
            Commute commute = Commute.builder()
                    .commuteDate(commuteRequestDto.commuteDate())
                    .employee(employee)
                    .commuteStart(commuteRequestDto.commuteStart())
                    .commuteEnd(commuteRequestDto.commuteEnd())
                    .commutePlace(CommutePlace.valueOf(commuteRequestDto.commutePlace()))
                    .commuteStatus(CommuteStatus.valueOf(commuteRequestDto.commuteStatus()))
                    .build();

            commuteRepository.save(commute);
        }

        return ResponseEntity.ok().body("출퇴근 시간이 추가되었습니다.");
    }

    public ResponseEntity<String> getOff(CommuteGetOffDto commuteGetOffDto, CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        Commute commute = commuteRepository.findTopByEmployeeOrderByCommuteDateDesc(employee).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMUTE));

        commute.updateCommuteInfo(commuteGetOffDto);

        // 변경된 출근 데이터 저장
        commuteRepository.save(commute);

        return ResponseEntity.ok().body("퇴근 시간이 추가되었습니다.");
    }

    public ResponseEntity<RecordResponseDto> getRecord(CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        LocalDate localDate = LocalDate.now();

        Optional<Commute> comOptional = commuteRepository.findByEmployeeAndCommuteDate(employee, localDate);



        RecordResponseDto recordResponseDto = null;
        if (comOptional.isPresent()) {
            recordResponseDto = RecordResponseDto.builder()
                    .commuteDate(comOptional.get().getCommuteDate() != null ? comOptional.get().getCommuteDate().toString() : null)
                    .startTime(comOptional.get().getCommuteStart() != null ? comOptional.get().getCommuteStart().toString() : null)
                    .endTime(comOptional.get().getCommuteEnd() != null ? comOptional.get().getCommuteEnd().toString() : null)
                    .way(comOptional.get().getCommutePlace() != null ? comOptional.get().getCommutePlace().toString() : null)
                    .color("full")
                    .name(employee.getName())
                    .build();
        }
        else {
            recordResponseDto = RecordResponseDto.builder()
                    .commuteDate(localDate.toString())
                    .name(employee.getName())
                    .startTime("00:00")
                    .endTime("00:00")
                    .build();
        }
        return ResponseEntity.ok().body(recordResponseDto);
    }


    public ResponseEntity<List<LocationResponseDto>> getLocation(CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        Company company = employee.getCompany(); // 유저 객체에 있는 회사 객체 저장


        List<LocationResponseDto> locationResponseDtos = new ArrayList<>();

        LocationResponseDto locationResponseDto = LocationResponseDto.builder()
                .companyName(company.getCompanyName())
                .latitude(company.getLatitude())
                .longitude(company.getLongitude())
                .id(company.getCompanyNo())
                .build();

        locationResponseDtos.add(locationResponseDto);

        LocationResponseDto location1 = LocationResponseDto.builder()
                .companyName("슈퍼코딩")
                .latitude(37.599384655)
                .longitude(127.079544212992)
                .id(company.getCompanyNo() + 1)
                .build();
        locationResponseDtos.add(location1);

        LocationResponseDto location2 = LocationResponseDto.builder()
                .companyName("리셋")
                .latitude(37.517331925853)
                .longitude(127.047377408384)
                .id(company.getCompanyNo() + 2)
                .build();
        locationResponseDtos.add(location2);

        LocationResponseDto location3 = LocationResponseDto.builder()
                .companyName("네이버")
                .latitude(37.5267849150832)
                .longitude(126.930167091136)
                .id(company.getCompanyNo() + 3)
                .build();
        locationResponseDtos.add(location3);

        LocationResponseDto location4 = LocationResponseDto.builder()
                .companyName("카카오")
                .latitude(37.4874127684412)
                .longitude(126.927123713685)
                .id(company.getCompanyNo() + 4)
                .build();
        locationResponseDtos.add(location4);



        return ResponseEntity.ok().body(locationResponseDtos);
    }

    public ResponseEntity<List<CommuteResponseDto>> getCommuteHistory(CustomUserDetails customUserDetails, int year, int month) {
        Employee employee = findById(customUserDetails);


        List<CommuteResponseDto> commuteResponseDtos = new ArrayList<>();

        List<Commute> commutes = commuteRepository.findByYearAndMonthAndEmployee(year, month, employee);
        List<Holiday> holidays = holidayRepository.findByYearAndMonthAndEmployee(year, month, employee);


        commutes.forEach(record -> {
            String color = (record.getCommuteStart() != null && record.getCommuteEnd() != null && Duration.between(record.getCommuteStart(), record.getCommuteEnd()).toHours() >= 5) ? "full" : "half";

            CommuteResponseDto dto = CommuteResponseDto.builder()
                    .id(record.getCommuteNo())
                    .name(record.getEmployee() != null ? record.getEmployee().getName() : null)
                    .start(record.getCommuteDate() != null ? record.getCommuteDate().toString() : null)
                    .way(record.getCommutePlace() != null ? record.getCommutePlace().getType() : null)
                    .color(color)
                    .startTime(record.getCommuteStart() != null ? record.getCommuteStart().format(DateTimeFormatter.ofPattern("HH:mm")) : null)
                    .endTime(record.getCommuteEnd() != null ? record.getCommuteEnd().format(DateTimeFormatter.ofPattern("HH:mm")) : null)
                    .build();

            commuteResponseDtos.add(dto);
        });

        holidays.forEach(leave -> {
            if (leave != null) {
                CommuteResponseDto dto = CommuteResponseDto.builder()
                        .id(leave.getLeaveNo())
                        .name(leave.getEmployee() != null ? leave.getEmployee().getName() : null)
                        .start(leave.getStartDate() != null ? leave.getStartDate().toString() : null)
                        .color("leave")
                        .build();

                commuteResponseDtos.add(dto);
            }
        });

        return ResponseEntity.ok().body(commuteResponseDtos);
    }

    public ResponseEntity<?> getStatus(CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        Optional<Commute> comOptional = commuteRepository.findTopByEmployeeOrderByCommuteDateDesc(employee);

        if(comOptional.isPresent()){
            StatusResponseDto statusResponseDto = StatusResponseDto.builder()
                    .status(comOptional.get().getCommuteStatus().getType())
                    .build();
            return ResponseEntity.ok().body(statusResponseDto);
        } else {
            return ResponseEntity.ok().body("null");
        }
    }

    public ResponseEntity<?> getDays(CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        LocalDate endDate = LocalDate.now();

        LocalDate startDate = endDate.minusYears(1);


        List<Commute> commutes = commuteRepository.findByEmployeeAndCommuteDateBetween(employee, startDate, endDate);

        int commuteDays = commutes.size();

        HomeResponseDto homeResponseDto = HomeResponseDto.builder()
                .commuteDays(commuteDays)
                .restLeaves(15)
                .totalLeaves(15)
                .build();


        return ResponseEntity.ok().body(homeResponseDto);
    }

    public Employee findById(CustomUserDetails customUserDetails) {
        return employeeRepository.findById(customUserDetails.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }



}
