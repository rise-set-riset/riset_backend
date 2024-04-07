package com.github.riset_backend.schedules.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.schedules.dto.employee.EmployeeDTO;
import com.github.riset_backend.schedules.dto.employee.ScheduleDTO;
import com.github.riset_backend.schedules.service.EmployeeSchedulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeSchedulesService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@AuthenticationPrincipal CustomUserDetails user) {
        List<EmployeeDTO> employees = employeeService.getAllEmployees(user);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/addEmployees")
    public ScheduleDTO addEmployees(@RequestBody ScheduleDTO scheduleDTO, @AuthenticationPrincipal CustomUserDetails user) {

        return employeeService.addEmployee(user, scheduleDTO);
    }

}
