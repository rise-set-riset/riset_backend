package com.github.riset_backend.login.commute.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record CommuteGetOffDto(
        @JsonFormat(pattern = "HH:mm") LocalTime commuteEnd,
        String commuteStatus) {
}
