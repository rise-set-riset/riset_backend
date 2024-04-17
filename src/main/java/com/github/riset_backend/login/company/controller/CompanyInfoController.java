package com.github.riset_backend.login.company.controller;


import com.github.riset_backend.login.company.dto.CompanyRequestDto;
import com.github.riset_backend.login.company.service.CompanyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyInfoController {
    private final CompanyInfoService companyInfoService;


    @PostMapping("/register-company")
    public ResponseEntity<String> register(CompanyRequestDto companyRequestDto) {
        return companyInfoService.register(companyRequestDto);
    }
}
