package com.github.riset_backend.login.commute.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;


public record CommuteDto(
                         @JsonFormat(pattern = "yyyy-MM-dd") LocalDate commuteDate,
                         @JsonFormat(pattern = "HH:mm") LocalTime commuteStart,
                         String commutePlace,
                         String commuteStatus) {
}
