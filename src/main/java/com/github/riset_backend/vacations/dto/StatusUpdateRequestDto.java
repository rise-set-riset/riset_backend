package com.github.riset_backend.vacations.dto;

public record StatusUpdateRequestDto(
        Long leaveNo,
        Status status
) {
}
