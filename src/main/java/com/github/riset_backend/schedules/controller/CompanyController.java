package com.github.riset_backend.schedules.controller;

import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.schedules.dto.CompanyScheduleRequestDto;
import com.github.riset_backend.schedules.dto.CompanyScheduleResponseDto;
import com.github.riset_backend.schedules.service.CompanySchedulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final CompanySchedulesService companySchedulesService;

    @PostMapping("/companySchedule")
    public CompanyScheduleRequestDto companyScheduleAdd(@RequestBody CompanyScheduleRequestDto request) {


        companySchedulesService.schedulesAdd(request);

        return request;
    }

    @GetMapping("/test")
    public Map<String, Map<String, List<CompanyScheduleResponseDto>>> companyScheduleAdd(@RequestParam("currentMonth") Long currentMonth) {


        return companySchedulesService.getAllCompanySchedules(currentMonth);
    }
}
