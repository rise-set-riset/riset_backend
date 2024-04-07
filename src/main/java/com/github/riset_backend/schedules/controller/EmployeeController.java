package com.github.riset_backend.schedules.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.schedules.dto.employee.EmployeeAddScheduleRequestDTO;
import com.github.riset_backend.schedules.dto.employee.EmployeeAddScheduleResponseDTO;
import com.github.riset_backend.schedules.dto.employee.EmployeeDTO;
import com.github.riset_backend.schedules.service.EmployeeSchedulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeSchedulesService employeeSchedulesService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("startDate") LocalDate startDate) {
        List<EmployeeDTO> employees = employeeSchedulesService.getAllEmployees(user, startDate);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/addEmployees")
    public EmployeeAddScheduleResponseDTO addEmployees(@RequestBody EmployeeAddScheduleRequestDTO request, @AuthenticationPrincipal CustomUserDetails user) {


        return employeeSchedulesService.addEmployee(user, request);
    }

}
