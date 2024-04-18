package com.github.riset_backend.login.employee.service;


import com.github.riset_backend.global.config.auth.JwtTokenProvider;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.department.entity.Department;
import com.github.riset_backend.login.department.repository.DepartmentRepository;
import com.github.riset_backend.login.employee.dto.EmployeeUpdateDto;
import com.github.riset_backend.login.employee.dto.PresetDto;
import com.github.riset_backend.login.employee.dto.ProfileEmployeeDto;
import com.github.riset_backend.login.employee.dto.ProfileUpdateDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import com.github.riset_backend.login.jobGrade.repository.JobGradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class PresetService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JobGradeRepository jobGradeRepository;
    private final DepartmentRepository departmentRepository;

    public ResponseEntity<String> preset(PresetDto presetDto, CustomUserDetails customUserDetails, String token) {
        Employee employee = employeeRepository.findById(customUserDetails.getEmployee().getEmployeeNo()).orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        String jwt = token.substring(7);

            employee.setRoles(Role.ROLE_ADMIN);

            jwtTokenProvider.setRole(jwt, "ROLE_ADMIN");

            Company company = presetDto.toEntity();
            companyRepository.save(company);
            employee.setCompany(company);
            employeeRepository.save(employee);

        return ResponseEntity.ok().body("설정이 완료되었습니다.");
    }


    @Transactional
    public List<ProfileEmployeeDto> getCompanyMembers(Long companyNo) {
        List<Employee> companyMembers = employeeRepository.findAllByCompany_CompanyNo(companyNo);
        return companyMembers.stream().map(ProfileEmployeeDto::new).collect(Collectors.toList());
    }

    public ProfileEmployeeDto updateProfile(Long employeeNo, ProfileUpdateDto dto) {


        Employee employee = employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
        );

        if(dto.getJobGradeId() != null) {
            JobGrade jobGrade = jobGradeRepository.findByGradeNo(dto.getJobGradeId()).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_JOB_GRADE)
            );
            employee.setJobGrade(jobGrade);
        }

        if(dto.getDepartId() != null) {
            Department department = departmentRepository.findById(dto.getDepartId()).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_DEPARTMENT)
            );
            employee.setDepartment(department);
        }

        if(dto.getSalary() != null) {
            employee.setSalary(dto.getSalary());
        }

        if (dto.getDateOfJoin() != null) {
            employee.setDateOfJoin(dto.getDateOfJoin());
        }

        if(dto.getPosition() != null) {
            employee.setPosition(dto.getPosition());
        }

        if(dto.getJob() != null) {
            employee.setJob(dto.getJob());
        }

        if (dto.getTotalHoliday() != null) {
            employee.setTotalAnnualLeave(dto.getTotalHoliday());
        }

        employeeRepository.save(employee);

        return new ProfileEmployeeDto(employee);

    }
}
