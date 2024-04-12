package com.github.riset_backend.vacations.dto;

import java.util.List;

public record StatusUpdateRequestDto(
        List<Long> leaveNo,
        Status status
) {
}
