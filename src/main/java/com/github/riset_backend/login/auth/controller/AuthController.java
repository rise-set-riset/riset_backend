package com.github.riset_backend.login.auth.controller;


import com.github.riset_backend.login.auth.dto.*;
import com.github.riset_backend.login.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "접속 관련 API", description = "유저 접속 관련 API")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "유저 회원 가입", description = "회원 가입을 하는 api입니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RequestSignUpDto requestSignUpDto){
        log.info("[POST]: 회원가입 요청");
        return authService.employeeSignup(requestSignUpDto);
    }

    @Operation(summary = "중복 확인", description = "ID 중복 확인을 하는 api입니다.")
    @PostMapping("/checkId")
    public boolean checkId(@RequestBody RequestCheckIdDto id) {
        return authService.checkId(id);
    }

    @Operation(summary = "유저 로그인", description = "로그인을 하는 api입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse httpServletResponse){
        log.info("[POST]: 로그인 요청");
        return authService.employeeLogin(requestLoginDto, httpServletResponse);
    }

    @Operation(summary = "AccessToken 재발급", description = "토큰 만료시 재발급을 해주는 api입니다.")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response, HttpServletRequest request){

        return authService.refresh(request, response);
    }


    @Operation(summary = "ID 찾기", description = "ID를 찾는 api입니다.")
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto findIdRequestDto) { return authService.findId(findIdRequestDto); }

    @Operation(summary = "PASSWORD 찾기", description = "비밀번호를 찾는 api입니다.")
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequestDto) { return authService.findPassword(findPasswordRequestDto);}


    @Operation(summary = "모든 유저 정보 조회", description = "저장된 모든 유저의 정보를 조회하는 api입니다.")
    @GetMapping("/userInfo")
    public ResponseEntity<List<?>> userInfo() {
        return authService.getUserInfo();
    }
}
