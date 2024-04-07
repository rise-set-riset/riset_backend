package com.github.riset_backend.vacations.controller;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.vacations.dto.RequestHoliday;
import com.github.riset_backend.vacations.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class controller {

    private final HolidayService holidayService;

    @PostMapping("/holiday")
    public String holiday(@RequestBody RequestHoliday holiday, @AuthenticationPrincipal CustomUserDetails user) {

        return holidayService.addHoliday(holiday, user).toString();

    }

}
