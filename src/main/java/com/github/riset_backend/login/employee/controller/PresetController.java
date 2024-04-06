package com.github.riset_backend.login.employee.controller;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.login.employee.PresetService;
import com.github.riset_backend.login.employee.dto.PresetAdminDto;
import com.github.riset_backend.login.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preset")
@RequiredArgsConstructor
@Slf4j
public class PresetController {
    private final PresetService presetService;

    @PostMapping("/role")
    public String presetRole(@RequestBody PresetAdminDto adminDto, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestHeader("Authorization") String token) {

        log.info("token: {}", token);
        log.info("[POST]: 사전설정 요청");
        return presetService.preset(adminDto, customUserDetails, token);
    }


}
