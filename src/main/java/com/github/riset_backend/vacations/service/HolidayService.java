package com.github.riset_backend.vacations.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.commute.entity.Commute;
import com.github.riset_backend.login.commute.repository.CommuteRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.vacations.dto.HolidayRequest;
import com.github.riset_backend.vacations.dto.LeaveType;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.vacations.dto.Status;
import com.github.riset_backend.vacations.repository.HolidayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class HolidayService {


    private static final Logger log = LoggerFactory.getLogger(HolidayService.class);
    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;

    //연차 반차 등록
    private LocalDateTime parseStartDateTime(String startTimeString) {
        LocalDate startDate = LocalDate.parse(startTimeString);
        return startDate.atStartOfDay();
    }

    @Transactional
    public String addHoliday(HolidayRequest request, CustomUserDetails user) {
        Employee employee = employeeRepository.findByEmployeeId(user.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Commute commute = commuteRepository.findTopByEmployeeOrderByCommuteDateDesc(employee).orElseThrow();

        boolean isHalfDay = request.isHalfDay();

        //true 반차, false 연차
        String type = isHalfDay ? LeaveType.HALF_DAY_LEAVE.name() : LeaveType.ANNUAL_LEAVE.name();
        Holiday holidayEntity;

        if (LeaveType.ANNUAL_LEAVE.name().equals(type)) {
            // 연차 처리

            holidayEntity = new Holiday();
            holidayEntity.addAll(employee, request.startTime(), LeaveType.ANNUAL_LEAVE, Status.PENDING, "연차");
            holidayRepository.save(holidayEntity);
            return "연차 등록";
        } else if (LeaveType.HALF_DAY_LEAVE.name().equals(type)) {
            // 반차 처리
            holidayEntity = new Holiday();
            holidayEntity.addHalf(employee, request.startTime(), LeaveType.HALF_DAY_LEAVE, request.startTime(), Status.PENDING, "반차");
            //반차 시작 시간을 퇴근 시간으로 수정
            commute.updateCommuteEnd(request.startTime().toLocalTime());
            commuteRepository.save(commute);
            holidayRepository.save(holidayEntity);
            return "반차 등록";
        } else {
            return "등록실패";
        }
    }


    private String formatDate(LocalDate date) {
        return date.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

}

