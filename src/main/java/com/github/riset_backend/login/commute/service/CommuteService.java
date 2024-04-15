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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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

        return ResponseEntity.ok().body("출근 시간이 추가되었습니다.");
    }

    public ResponseEntity<?> addCommute(CustomUserDetails customUserDetails, CommuteRequestDto commuteRequestDto) {
        Employee employee = findById(customUserDetails);

        Commute commute = Commute.builder()
                .commuteDate(commuteRequestDto.commuteDate())
                .employee(employee)
                .commuteStart(commuteRequestDto.commuteStart())
                .commuteEnd(commuteRequestDto.commuteEnd())
                .commutePlace(CommutePlace.valueOf(commuteRequestDto.commutePlace()))
                .commuteStatus(CommuteStatus.valueOf(commuteRequestDto.commuteStatus()))
                .build();

        commuteRepository.save(commute);

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
        LocalTime localTime = LocalTime.now();

        Optional<Commute> comOptional = commuteRepository.findByEmployeeAndCommuteDate(employee, localDate);

        if(comOptional.isPresent()){
            RecordResponseDto recordResponseDto = RecordResponseDto.builder()
                    .commuteDate(comOptional.get().getCommuteDate().toString())
                    .commuteStart(comOptional.get().getCommuteStart().toString())
                    .commutePlace(comOptional.get().getCommutePlace().toString())
                    .name(employee.getName())
                    .build();
            return ResponseEntity.ok().body(recordResponseDto);
        } else {
            RecordResponseDto recordResponseDto = RecordResponseDto.builder()
                    .commuteDate(localDate.toString())
                    .name(employee.getName())
                    .commuteStart(localTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                    .build();
            return ResponseEntity.ok().body(recordResponseDto);
        }
    }


    public ResponseEntity<List<LocationResponseDto>> getLocation(CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        Company company = employee.getCompany(); // 유저 객체에 있는 회사 객체 저장

        // 회사 객체로 본사 번호 조회 및 저장 후 반환(리스트)
        List<Company> childCompanies = companyRepository.findAllByParentCompanyNo(company.getParentCompanyNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMPANY));

        List<LocationResponseDto> response = new ArrayList<>();

        for(Company parentCompany: childCompanies){
            LocationResponseDto locationResponseDto = new LocationResponseDto(
                    parentCompany.getCompanyNo(),
                    parentCompany.getCompanyName(),
                    parentCompany.getLongitude(),
                    parentCompany.getLatitude());
            response.add(locationResponseDto);
        }

        return ResponseEntity.ok().body(response);
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

    public ResponseEntity<StatusResponseDto> getStatus(CustomUserDetails customUserDetails) {
        Employee employee = findById(customUserDetails);

        Optional<Commute> comOptional = commuteRepository.findTopByEmployeeOrderByCommuteDateDesc(employee);

        if(comOptional.isPresent()){
            StatusResponseDto dto = new StatusResponseDto(comOptional.get().getCommuteStatus().toString());
            return ResponseEntity.ok().body(dto);
        } else {
            return null;
        }
    }

    public Employee findById(CustomUserDetails customUserDetails) {
        return employeeRepository.findById(customUserDetails.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }


}
