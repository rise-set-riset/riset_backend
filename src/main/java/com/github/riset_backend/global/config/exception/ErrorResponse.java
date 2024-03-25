package com.github.riset_backend.global.config.exception;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus status,
        String message
) {
}

