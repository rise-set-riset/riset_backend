package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Role;

public record PresetDto(String companyName, String companyAddr, Double latitude, Double longitude){
    public Company toEntity() {
        return new Company(latitude, longitude, companyName, companyAddr);
    }
}
