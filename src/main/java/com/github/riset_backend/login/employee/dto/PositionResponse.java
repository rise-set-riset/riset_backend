package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.Settlement.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponse {

    private Long positionId;
    private String positionName;

    public PositionResponse(Position position) {
        this.positionId = position.getPositionNo();
        this.positionName = position.getPositionName();
    }
}
