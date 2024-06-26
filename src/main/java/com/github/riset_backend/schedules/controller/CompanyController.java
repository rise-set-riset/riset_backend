package com.github.riset_backend.schedules.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.schedules.dto.company.*;
import com.github.riset_backend.schedules.service.CompanySchedulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "회사 일정 등록,수정,조회, 삭제 api 입니다", description = "회사의 일정 등록,수정,삭제,조회 api 입니다")
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
    private final CompanySchedulesService companySchedulesService;

    //회사 일정 추가
    @PostMapping("/companySchedule")
    @Operation(summary = "회사일정을 등록하는 api 입니다", description = "회사일정을 등록하는 api 입니다")
    public ResponseEntity<CompanyScheduleRequestDto> companyScheduleAdd(@RequestBody CompanyScheduleRequestDto request, @AuthenticationPrincipal CustomUserDetails user) {
        CompanyScheduleRequestDto a= companySchedulesService.schedulesAdd(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }

    //회사일정의 해당 월 일정 가져오기
    @GetMapping("/get")
    @Operation(summary = "회사의 해당 월 일정을 가져오는 api 입니다", description = "회사의 해당 월 일정을 가져오는 api 입니다")
    public ResponseEntity<List<CompanyScheduleResponseDto>> getCompanySchedules(@RequestParam("currentMonth") String currentMonth, @AuthenticationPrincipal CustomUserDetails user) {
        List<CompanyScheduleResponseDto> schedules = companySchedulesService.getAllCompanySchedules(currentMonth, user);
        return ResponseEntity.status(HttpStatus.OK).body(schedules);
    }


    @PatchMapping("/update")
    @Operation(summary = "회사의 해당 일정을 수정하는 api 입니다", description = "회사의 해당 일정을 수정하는 api 입니다")
    public ResponseEntity<CompanyUpdateDateTimeDto> companyScheduleUpdate(@RequestBody CompanyUpdateDateTimeDto request, @AuthenticationPrincipal CustomUserDetails user) {
        companySchedulesService.updateComSchedule(request);
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회사의 해당 일정을 삭제하는 api 입니다", description = "회사의 해당 일정을 삭제하는 api 입니다")
    public ResponseEntity<String> companyScheduleDelete(@RequestParam("id") Long scheduleId, @AuthenticationPrincipal CustomUserDetails user) {
        String result = companySchedulesService.deleteCompanySchedule(scheduleId, user);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }


}
