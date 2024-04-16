package com.github.riset_backend.login.auth.dto;

import lombok.Builder;

@Builder
public record AllUserResponseDto (String name, Long profileId, String profilePath, String profileName) {

    public AllUserResponseDto(String name, Long profileId, String profilePath, String profileName) {
        this.name = name;
        this.profileId = profileId;
        this.profilePath = profilePath;
        this.profileName = profileName;
    }
}
