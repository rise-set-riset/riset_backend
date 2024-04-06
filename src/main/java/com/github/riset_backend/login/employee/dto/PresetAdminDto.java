package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Role;

public record PresetAdminDto(String companyName, String companyAddr, Integer invitedCode, String role){
    public PresetAdminDto(Company company, Integer invitedCode, Role role){
        this(company.getCompanyName(), company.getCompanyAddr(), invitedCode, role.getType());
    }
}
