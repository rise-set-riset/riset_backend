package com.github.riset_backend.login.commute.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


@Builder
@Getter
public class CommuteResponseDto {
    private Long id;
    private String way; // 예: "본사(풀근무)", "재택", "외근", "본사(반차)"
    private String name;
    private String start;
    private String color;
    private String startTime;
    private String endTime;
}
