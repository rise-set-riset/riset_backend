package com.github.riset_backend.login.department.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.department.dto.DepartMentRequestDto;
import com.github.riset_backend.login.department.dto.DepartResponseDto;
import com.github.riset_backend.login.department.entity.Department;
import com.github.riset_backend.login.department.repository.DepartmentRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/depart")
public class DepartMentController {
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;


    @PostMapping("/add")
    public ResponseEntity<String> addDepartment(@RequestBody DepartMentRequestDto name, @AuthenticationPrincipal CustomUserDetails user) {
        //유저 검증, 회사 검증
        Company company = companyRepository.findById(user.getEmployee().getCompany().getCompanyNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow();
        try {
            Department department = new Department(name, company);
            employee.setDepartment(department);
            departmentRepository.save(department);
            return ResponseEntity.status(HttpStatus.OK).body(department.getDeptName() + " 이 등록되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("등록 실패");
        }
    }



    @GetMapping("")
    public ResponseEntity<List<DepartResponseDto>> getCompanyDepart (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long companyNo = customUserDetails.getEmployee().getCompany().getCompanyNo();
        List<DepartResponseDto> departments = departmentRepository.findAllByCompany_CompanyNo(companyNo).stream().map(DepartResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(departments);
    }
}