package com.github.riset_backend.manageCompany.controller;


import com.github.riset_backend.manageCompany.service.MailService;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/preset")
@RestController
@Tag(name = "관리자가 메일을 발송하는 api", description = "관리자는 코드가 담긴 메일을 전송할 수 있습니다")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    @ResponseStatus
    @Operation(summary = "관리자가 이메일 발송하는 api", description = "관리자가 이메일 발송을 할 수 있습니다.")
    public void mail(@RequestParam("email") String email, @AuthenticationPrincipal CustomUserDetails user) {


        mailService.answerMail(email, user);

    }

    @PostMapping("/employee")
    @ResponseStatus
    @Operation(summary = "code 확인하고, 코드를 회사 자동 등록", description = "코드 확인 후 등록")
    public void checkCodeUser(@RequestParam("code") String code, @RequestHeader("Authorization") String token, @AuthenticationPrincipal CustomUserDetails user) {
        mailService.CompanyCode(code, token, user);
    }
}