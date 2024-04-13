package com.github.riset_backend.schedules.dto.employee;



public record EmployeeDTO(
        Long employeeId,
        String name,
        String department,
        String position,
        String image
) {
}
