package com.github.riset_backend.login.employee;


import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.dto.PresetAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PresetService {
    private final CompanyRepository companyRepository;

    public ResponseEntity<String> admin(PresetAdminDto adminDto) {
//        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(customUserDetails.getEmployee().getEmployeeId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER)));

        Company company = Company.builder()
                .companyName(adminDto.companyName())
                .companyAddr(adminDto.companyAddr())
                .build();

        companyRepository.save(company);

        return ResponseEntity.ok("설정이 완료되었습니다.");
    }
}
