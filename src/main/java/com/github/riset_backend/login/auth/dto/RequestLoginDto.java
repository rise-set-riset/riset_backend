package com.github.riset_backend.login.auth.dto;

import com.github.riset_backend.login.employee.entity.Employee;

public record RequestLoginDto(String id, String password){
    public RequestLoginDto(Employee employee){
        this(employee.getEmployeeId(), employee.getPassword());
    }
}
