package com.github.riset_backend.login.auth.controller;


import com.github.riset_backend.login.auth.dto.FindIdRequestDto;
import com.github.riset_backend.login.auth.dto.FindPasswordRequestDto;
import com.github.riset_backend.login.auth.dto.RequestLoginDto;
import com.github.riset_backend.login.auth.dto.RequestSignUpDto;
import com.github.riset_backend.login.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RequestSignUpDto requestSignUpDto){
        log.info("[POST]: 회원가입 요청");
        return authService.employeeSignup(requestSignUpDto);
    }

    @GetMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestBody String Id) {
        return authService.checkId(Id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse httpServletResponse){
        log.info("[POST]: 로그인 요청");
        return authService.employeeLogin(requestLoginDto, httpServletResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response, HttpServletRequest request){

        return authService.refresh(request, response);
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto findIdRequestDto) { return authService.findId(findIdRequestDto); }

    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequestDto) { return authService.findPassword(findPasswordRequestDto);}
}
