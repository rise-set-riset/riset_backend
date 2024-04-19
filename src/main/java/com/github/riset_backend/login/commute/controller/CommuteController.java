package com.github.riset_backend.login.commute.controller;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.login.commute.dto.*;
import com.github.riset_backend.login.commute.service.CommuteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "출퇴근 API", description = "출퇴근 등록 및 조회")
@RequestMapping("/commute")
public class CommuteController {
    private final CommuteService commuteService;

    @Operation(summary = "출근 등록", description = "출근 데이터를 받는 API입니다.")
    @PostMapping("/register-commute")
    public ResponseEntity<?> commute(@RequestBody CommuteDto commuteDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return commuteService.registerCommute(commuteDto, customUserDetails);
    }

    @Operation(summary = "퇴근 등록", description = "퇴근 데이터를 업데이트 하는 API입니다.")
    @PutMapping("/get-off")
    public ResponseEntity<String> getOff(@RequestBody CommuteGetOffDto commuteGetOffDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return commuteService.getOff(commuteGetOffDto, customUserDetails);
    }

    @Operation(summary = "출퇴근 등록", description = "유저가 직접 입력해서 출퇴근 데이터를 받는 API입니다.")
    @PatchMapping("/add-commute")
    public ResponseEntity<?> addCommute(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CommuteRequestDto commuteResponseDto) {
        return commuteService.addCommute(customUserDetails, commuteResponseDto);
    }

    @Operation(summary = "출퇴근 등록 화면 조회", description = "출퇴근 등록 화면을 조회하는 API입니다.")
    @GetMapping("/record")
    public ResponseEntity<RecordResponseDto> getRecord(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return commuteService.getRecord(customUserDetails);
    }


    @Operation(summary = "출퇴근 현황 조회", description = "년도와 월에 해당하는 출퇴근 현황을 조회하는 API입니다.")
    @GetMapping("/commute-history")
    public ResponseEntity<List<CommuteResponseDto>> commuteHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam int year, @RequestParam int month) {
        return commuteService.getCommuteHistory(customUserDetails, year, month);
    }

    @Operation(summary = "회사 위치 조회", description = "회사 위치를 조회하는 API입니다.")
    @GetMapping("/company-location")
    public ResponseEntity<List<LocationResponseDto>> companyLocation(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return commuteService.getLocation(customUserDetails);
    }

    @Operation(summary = "출근 상태 조회", description = "출근 여부를 조회하는 API입니다.")
    @GetMapping("/get-status")
    public ResponseEntity<?> getStatus(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return commuteService.getStatus(customUserDetails);
    }

    @Operation(summary = "출근 일수 조회", description = "1년간의 출근일을 조회하는 API입니다.")
    @GetMapping("/days")
    public ResponseEntity<?> getDays(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return commuteService.getDays(customUserDetails);
    }
}
