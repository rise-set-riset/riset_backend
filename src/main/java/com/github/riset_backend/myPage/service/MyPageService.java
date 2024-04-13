package com.github.riset_backend.myPage.service;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.myPage.dto.MyPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {


    private final EmployeeRepository employeeRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //첫 진입 조회
    public MyPageResponseDto MyPageALL(CustomUserDetails user) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        MyPageResponseDto response = new MyPageResponseDto(
                employee.getEmployeeId(),
                employee.getBirth(),
                employee.getEmail(),
                employee.getTelNumber(),
                employee.getZipCode(),
                employee.getDepartment().getDeptName(),
                employee.getPosition(),
                employee.getJob(),
                employee.getMyImage().getFilePath()
        );
        return response;

    }
}
