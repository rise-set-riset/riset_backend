package com.github.riset_backend.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MailRequestDto(
        @NotBlank
        @Schema(description = "받는 사람 이메일", example = "def@gmail.com")
        String email,  //받는사람
        @NotBlank
        @Schema(description = "제목!", example = "너 내 동료가 도도도도독")
        String content  //제목
) {
}
