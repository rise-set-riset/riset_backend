package com.github.riset_backend.login.employee.controller;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.login.employee.dto.PresetDto;
import com.github.riset_backend.login.employee.dto.ProfileEmployeeDto;
import com.github.riset_backend.login.employee.dto.ProfileUpdateDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.service.PresetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preset")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "사전 설정 API", description = "관리자 사전 설정")
public class PresetController {
    private final PresetService presetService;

    @PostMapping("/admin")
    @Operation(summary = "관리자 사전 설정", description = "관리자로 등록하는 API입니다.")
    public ResponseEntity<String> presetRole(@RequestBody PresetDto presetDto, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestHeader("Authorization") String token) {
        return presetService.preset(presetDto, customUserDetails, token);
    }

    @GetMapping()
    public ResponseEntity<Employee> getEmployee (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(customUserDetails.getEmployee());
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileEmployeeDto>> getCompanyMembers(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<ProfileEmployeeDto> profileEmployeeDtos = presetService.getCompanyMembers(customUserDetails.getEmployee().getCompany().getCompanyNo());
        return ResponseEntity.ok(profileEmployeeDtos);
    }

    @PatchMapping("/profiles/{employeeNo}")
    public ResponseEntity<ProfileEmployeeDto> updateEmployeeProfiles (@PathVariable Long employeeNo,
                                                                      @RequestBody ProfileUpdateDto dto) {
       ProfileEmployeeDto profile = presetService.updateProfile(employeeNo, dto);
       return ResponseEntity.ok(profile);
    }
}
