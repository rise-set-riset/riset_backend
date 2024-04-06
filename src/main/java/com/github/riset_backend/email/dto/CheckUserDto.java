package com.github.riset_backend.email.dto;

public record CheckUserDto(
        String code,
        String token,
        String cont
) {
}
