package com.github.riset_backend.vacations.controller;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.vacations.dto.*;
import com.github.riset_backend.vacations.service.EmployeeLeaveSchedulesService;
import com.github.riset_backend.vacations.service.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "직원 연차,반차 신청 수정, 조회, 삭제, 승인상태 변경 하는 api 입니다", description = "연차, 반차, 조회, 수정 승인상태 변경 신청할 수 있습니다")
@RequestMapping("/api/holiday")
public class VacationsController {

    private final HolidayService holidayService;
    private final EmployeeLeaveSchedulesService employeeLeaveSchedulesService;

    @PostMapping("/addHoliday")
    @Operation(summary = "연차/반차 신청하는 api 입니다", description = "연차/반차 신청할 수 있습니다")
    public String holiday(@RequestBody RequestHoliday holiday, @AuthenticationPrincipal CustomUserDetails user) {
        return holidayService.addHoliday(holiday, user).toString();
    }

    @PatchMapping("/addUpdateEmployee")
    @Operation(summary = "연차/반차 수정하는 api 입니다", description = "연차/반차 수정하는 api 입니다")
    public String update(@RequestBody UpdateEmployeeRequestDto holiday, @AuthenticationPrincipal CustomUserDetails user) {
        employeeLeaveSchedulesService.updateEmployeeHoliday(user, holiday);
        return "수정 완료";
    }


    @PatchMapping("/status")
    @Operation(summary = "직원 승인상태 변경하는 api", description = "승인 상태 변경")
    public String accessEmployee(@AuthenticationPrincipal CustomUserDetails user, @RequestBody StatusUpdateRequestDto request) {
        employeeLeaveSchedulesService.accessEmployeeHoliday(user, request);
        return "승인 수정완료";
    }


    @GetMapping("/holidayGet")
    @Operation(summary = "직원 연차/반차 목록을 조회하는 api 입니다", description = "연차/반차를 조회합니다")
    public List<StatusAccessDto> holidayGet(@AuthenticationPrincipal CustomUserDetails user, @RequestBody StatusDTO statusDTO) {
        return employeeLeaveSchedulesService.getEmployeeHoliday(user, statusDTO.status());
    }
}
