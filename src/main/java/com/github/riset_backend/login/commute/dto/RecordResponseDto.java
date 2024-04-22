package com.github.riset_backend.login.commute.dto;


import lombok.Builder;

@Builder
public record RecordResponseDto(
        String way,
        String name,
        String commuteDate,
        String startTime,
        String endTime,
        String color) {
}
