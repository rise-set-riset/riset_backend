package com.github.riset_backend.myPage.service;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.department.entity.Department;
import com.github.riset_backend.login.department.repository.DepartmentRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.myPage.dto.ModifyUserPasswordRequest;
import com.github.riset_backend.myPage.dto.MyPageInfo;
import com.github.riset_backend.myPage.dto.UpdateCompanyAddress;
import com.github.riset_backend.myPage.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;


    //첫 진입 조회
    public MyPageInfo MyPageALL(CustomUserDetails user) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        String filePath = "";
        if (employee.getMyImage() != null && employee.getMyImage().getFilePath() != null) {
            filePath = employee.getMyImage().getFilePath();
        }

        MyPageInfo response = new MyPageInfo(
                filePath,
                employee.getName(),
                employee.getJobGrade(),
                employee.getJob(),
                employee.getDateOfJoin(),
                employee.getDepartment().getDeptName(),
                employee.getPosition(),
                employee.getTelNumber(),
                employee.getAddress(),
                15,
                5000
        );
        return response;
    }

    @Transactional
    public String updatePassword(ModifyUserPasswordRequest request, CustomUserDetails user) {
        try {
            Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

            String newPassword = request.newPassword();
            String encryptedPassword = passwordEncoder.encode(newPassword);
            employee.setPassword(encryptedPassword);
            employeeRepository.save(employee);
            return "비밀번호 변경 완료";
        } catch (BusinessException be) {
            return "수정 실패";
        }
    }


    @Transactional
    public String updateUser(UserUpdateDto request, CustomUserDetails user) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Department department = departmentRepository.findById(employee.getDepartment().getDeptNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_DEPARTMENT));

        try {
            employee.userUpdate(
                    request.name(),
                    request.telNo(),
                    request.address(),
                    request.joiningDate()
            );

            department.setDeptName(request.departmentName());

            departmentRepository.save(department);
            employeeRepository.save(employee);
            return "수정완료";
        } catch (Exception e) {
            return e.getMessage() + " 에러";
        }
    }

    public ResponseEntity<?> deleteUser(CustomUserDetails customUserDetails) {
        Optional<Employee> employee = employeeRepository.findByEmployeeNo(customUserDetails.getEmployee().getEmployeeNo());

        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return ResponseEntity.ok().body("회원 탈퇴가 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 요청입니다.");
        }
    }

    public String updateCompanyAddress(CustomUserDetails user, UpdateCompanyAddress address) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Company company = companyRepository.findById(employee.getCompany().getCompanyNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        if (employee.getRoles().equals(Role.ROLE_ADMIN)) {
            company.updateAddress(address.address());
            companyRepository.save(company);
            return "업데이트 완료";
        }
        return "업데이트 실패";
    }
}