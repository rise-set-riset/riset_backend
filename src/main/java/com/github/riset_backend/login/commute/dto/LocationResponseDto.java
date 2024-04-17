package com.github.riset_backend.login.commute.dto;


import lombok.Builder;

@Builder
public record LocationResponseDto(Long id, String companyName, Double longitude, Double latitude) {
}
