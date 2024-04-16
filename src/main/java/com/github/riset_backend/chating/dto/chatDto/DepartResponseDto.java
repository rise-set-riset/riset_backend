package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.login.department.entity.Department;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartResponseDto {

    private Long departmentId;
    private String departmentName;

    public DepartResponseDto(Department department) {
        this.departmentId = department.getDeptNo();
        this.departmentName = department.getDeptName();
    }

}
