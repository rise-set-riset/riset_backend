package com.github.riset_backend.myPage.dto;

public record UserUpdateDto(
        String name,
        String departmentName,
        String telNo,
        String address,
        String joiningDate

) {
}

