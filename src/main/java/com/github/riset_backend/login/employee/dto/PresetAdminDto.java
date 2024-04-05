package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.login.company.entity.Company;

public record PresetAdminDto(String companyName, String companyAddr){
    public PresetAdminDto(Company company){
        this(company.getCompanyName(), company.getCompanyAddr());
    }
}
