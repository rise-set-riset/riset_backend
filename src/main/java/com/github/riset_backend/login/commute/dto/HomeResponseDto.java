package com.github.riset_backend.login.commute.dto;


import lombok.Builder;

@Builder
public record HomeResponseDto(int commuteDays, int restLeaves, int totalLeaves) {
}
