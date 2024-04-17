package com.github.riset_backend.login.department.dto;

import com.github.riset_backend.login.department.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartResponseDto {

    private Long departId;
    private String departName;

    public DepartResponseDto(Department department) {
        this.departId = department.getDeptNo();
        this.departName = department.getDeptName();
    }
}
