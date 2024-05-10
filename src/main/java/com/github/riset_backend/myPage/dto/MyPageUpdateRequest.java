package com.github.riset_backend.myPage.dto;

public record MyPageUpdateRequest(
        String email,
        String name,
        String zipCOde,
        String TelNo
        ) {
}
