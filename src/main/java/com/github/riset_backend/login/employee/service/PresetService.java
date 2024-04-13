package com.github.riset_backend.login.employee.service;


import com.github.riset_backend.global.config.auth.JwtTokenProvider;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.dto.PresetDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
@Slf4j
public class PresetService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<String> preset(PresetDto presetDto, CustomUserDetails customUserDetails, String token) {
        Employee employee = employeeRepository.findById(customUserDetails.getEmployee().getEmployeeNo()).orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        String jwt = token.substring(7);

            employee.setRoles(Role.ROLE_ADMIN);
            employeeRepository.save(employee);
            jwtTokenProvider.setRole(jwt, "ROLE_ADMIN");
            Company company = Company.builder()
                    .companyName(presetDto.companyName())
                    .companyAddr(presetDto.companyAddr())
                    .build();
            companyRepository.save(company);

        return ResponseEntity.ok().body("설정이 완료되었습니다.");
    }
}
