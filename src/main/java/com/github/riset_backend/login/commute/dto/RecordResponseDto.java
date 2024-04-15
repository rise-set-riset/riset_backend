package com.github.riset_backend.login.commute.dto;


import lombok.Builder;

@Builder
public record RecordResponseDto(
        String commutePlace,
        String name,
        String commuteDate,
        String commuteStart) {
}
