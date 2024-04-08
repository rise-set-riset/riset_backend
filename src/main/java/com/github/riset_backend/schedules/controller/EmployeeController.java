package com.github.riset_backend.schedules.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.schedules.dto.company.UpdateComScheduleDto;
import com.github.riset_backend.schedules.dto.employee.EmployeeAddScheduleRequestDTO;
import com.github.riset_backend.schedules.dto.employee.EmployeeAddScheduleResponseDTO;
import com.github.riset_backend.schedules.dto.employee.EmployeeDTO;
import com.github.riset_backend.schedules.service.EmployeeSchedulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
@Tag(name = "직원 일정 등록, 수정, 삭제, 조회 api 입니다.", description = "직원 일정 등록, 수정, 삭제, 조회 api 입니다.")
public class EmployeeController {


    private final EmployeeSchedulesService employeeSchedulesService;

    @GetMapping
    @Operation(summary = "첫 진입 조회 api 입니다", description = "첫 진입 조회 api 입니다")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("startDate") LocalDate startDate) {
        List<EmployeeDTO> employees = employeeSchedulesService.getAllEmployees(user, startDate);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/addEmployees")
    @Operation(summary = "직원 일정 등록하는 api 입니다", description = "직원 일정을 등록할 수 있습니다")
    public EmployeeAddScheduleResponseDTO addEmployees(@RequestBody EmployeeAddScheduleRequestDTO request, @AuthenticationPrincipal CustomUserDetails user) {
        return employeeSchedulesService.addEmployee(user, request);
    }

    @PatchMapping("/updateSchedule")
    @Operation(summary = "직원 일정 수정하는 api 입니다", description = "직원 일정을 수정할 수 있습니다")
    public ResponseEntity<UpdateComScheduleDto> updateEmployees(@RequestBody UpdateComScheduleDto schedule, @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok().body(employeeSchedulesService.updateSchedule(schedule, user));
    }

    @DeleteMapping("/deleteSchedule")
    @Operation(summary = "직원 연차/반차 수정하는 api 입니다", description = "직원 연차/반차 수정할 수 있습니다")
    public ResponseEntity<String> updateEmployees(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("id") Long id) {
        return ResponseEntity.ok().body(employeeSchedulesService.deleteSchedule(user, id));
    }

}
