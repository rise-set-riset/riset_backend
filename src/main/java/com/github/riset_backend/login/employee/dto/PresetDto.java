package com.github.riset_backend.login.employee.dto;

import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.employee.entity.Role;

public record PresetDto(String companyName, String companyAddr, Double Latitude, Double Longitude){
    public Company toEntity() {
        return new Company(Latitude, Longitude, companyName, companyAddr);
    }
}
