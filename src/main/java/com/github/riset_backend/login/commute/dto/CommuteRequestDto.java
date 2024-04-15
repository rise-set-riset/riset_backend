package com.github.riset_backend.login.commute.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record CommuteRequestDto(
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate commuteDate,
        @JsonFormat(pattern = "HH:mm") LocalTime commuteStart,
        @JsonFormat(pattern = "HH:mm") LocalTime commuteEnd,
        String commutePlace,
        String commuteStatus
) {
}
