package com.github.riset_backend.login.company.service;


import com.github.riset_backend.login.company.dto.CompanyRequestDto;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyInfoService {
    private final CompanyRepository companyRepository;

    public ResponseEntity<String> register(CompanyRequestDto companyRequestDto) {
        Company company = Company.builder()
                .parentCompanyNo(companyRequestDto.parentCompanyNo())
                .companyAddr(companyRequestDto.address())
                .latitude(companyRequestDto.latitude())
                .longitude(companyRequestDto.longitude())
                .build();

        companyRepository.save(company);

        return ResponseEntity.ok().body("회사 정보가 등록되었습니다.");
    }
}
