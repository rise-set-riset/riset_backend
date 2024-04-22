package com.github.riset_backend.login.auth.dto;


import com.github.riset_backend.login.employee.entity.Employee;



public record RequestSignUpDto(String id, String password, String name, String phone){

    public RequestSignUpDto(Employee employee){
        this(employee.getEmployeeId(), employee.getPassword(), employee.getName(), employee.getPhoneNumber());
    }
}

