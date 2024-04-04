package com.github.riset_backend.login.auth.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {
    private String accesstoken;

    private String refreshtoken;
}
