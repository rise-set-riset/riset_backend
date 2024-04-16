package com.github.riset_backend.login.auth.dto;

import lombok.Builder;

@Builder
public record AllUserResponseDto (String name, Long profileId, String profilePath, String profileName, Long employeeId) {
}
