package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Role;

public record PresetDto(String companyName, String companyAddr, Integer invitedCode, String role){
    public PresetDto(Company company, Integer invitedCode, Role role){
        this(company.getCompanyName(), company.getCompanyAddr(), invitedCode, role.getType());
    }
}
