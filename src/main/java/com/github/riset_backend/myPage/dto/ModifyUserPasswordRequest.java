package com.github.riset_backend.myPage.dto;

public record ModifyUserPasswordRequest(
        String oldPassword,
        String newPassword
) {
}
