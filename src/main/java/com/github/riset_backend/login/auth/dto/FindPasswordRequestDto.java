package com.github.riset_backend.login.auth.dto;

public record FindPasswordRequestDto(
        String id,
        String email
) {
}
