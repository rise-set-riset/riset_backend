package com.github.riset_backend.login.commute.controller;


import com.amazonaws.Response;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.login.commute.dto.CommuteDto;
import com.github.riset_backend.login.commute.dto.CommuteResponseDto;
import com.github.riset_backend.login.commute.service.CommuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/commute")
public class CommuteController {
    private final CommuteService commuteService;

    @PostMapping("/register-commute")
    public ResponseEntity<?> commute(@RequestBody CommuteDto commuteDto){
        return commuteService.addCommute(commuteDto);
    }

    @GetMapping("/commute-history")
    public List<CommuteResponseDto> commuteHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return commuteService.getCommuteHistory(customUserDetails);
    }
}
