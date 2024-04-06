package com.github.riset_backend.login.employee;


import com.github.riset_backend.global.config.auth.JwtTokenProvider;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.dto.PresetAdminDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class PresetService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String preset(PresetAdminDto adminDto, CustomUserDetails customUserDetails, String token) {
        Employee employee = employeeRepository.findById(customUserDetails.getEmployee().getEmployeeNo()).orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        String jwt = token.substring(7);

        if (adminDto.invitedCode() == null) {
            employee.setRoles(Role.valueOf(adminDto.role()));
            employeeRepository.save(employee);
            jwtTokenProvider.setRole(jwt, adminDto.role());
            Company company = Company.builder()
                    .companyName(adminDto.companyName())
                    .companyAddr(adminDto.companyAddr())
                    .build();
            companyRepository.save(company);
        } else {
            //TODO 초대 코드 비교 로직
            employee.setRoles(Role.valueOf(adminDto.role()));
            employeeRepository.save(employee);
            jwtTokenProvider.setRole(jwt, adminDto.role());
        }
        return "설정이 완료되었습니다.";
    }
}
