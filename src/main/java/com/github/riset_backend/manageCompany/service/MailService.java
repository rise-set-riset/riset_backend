package com.github.riset_backend.manageCompany.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.manageCompany.entity.Email;
import com.github.riset_backend.manageCompany.repository.EmailRepository;
import com.github.riset_backend.global.config.auth.JwtTokenProvider;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.github.riset_backend.global.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Value("${spring.mail.username}")
    private String sendEmail;

    // 이메일 발송
    public String answerMail(String email, CustomUserDetails user) {

        //유저 확인
        Optional<Employee> userName = Optional.ofNullable(employeeRepository.findByEmployeeId(user.getUsername()).orElseThrow(
                () -> new BusinessException(NOT_USER, "없는 유저 입니다.")));


        //회사 확인, ADMIN 권한확인
        if (user.getEmployee().getRoles().name().equals(Role.ROLE_ADMIN.name()) && userName.get().getCompany().getCompanyNo() != null) {
            Long companyNo = userName.get().getCompany().getCompanyNo();

            String code = RandomCodeGenerator.generateCode() + companyNo;
            // 이메일 전송
            sendVerificationEmail(email, code);
            Email emailEntity = Email.builder()
                    .code(code)
                    .employeeId(userName.get().getEmployeeNo())
                    .build();

            emailRepository.save(emailEntity);//db 저장
        } else {
            return "발급 실패";
        }
        return email + "에 이메일을 발송하였습니다";
    }


    @Transactional
    public String CompanyCode(String code, String token, CustomUserDetails user) {

        String jwt = token.substring(7);


        // 이메일 리포지토리에서 코드를 사용하여 직원 ID를 가져옴
        Email emailUser = emailRepository.findByCode(code)
                .orElseThrow(() -> new BusinessException(NOT_USER, "이메일이 코드와 연관되어 있지 않습니다."));

        // 코드에서 숫자만 추출하여 회사 ID로 사용
        Long numbersOnly = Long.valueOf(code.replaceAll("[^0-9]", ""));

        // 회사를 찾음
        Company companyNo = companyRepository.findById(numbersOnly)
                .orElseThrow(() -> new BusinessException(NOT_USER, "해당 회사를 찾을 수 없습니다."));

        // 직원을 찾음
        Employee employee = employeeRepository.findByEmployeeNo(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(NOT_USER, "해당 직원을 찾을 수 없습니다."));

        // 직원의 회사를 업데이트
        employee.setCompany(companyNo);
        jwtTokenProvider.setRole(jwt, Role.ROLE_EMPLOYEE.name());
        employee.setRoles(Role.ROLE_EMPLOYEE);


        employeeRepository.save(employee);
        emailRepository.delete(emailUser);
        return companyNo.getCompanyName() + "회사에 등록되었습니다";



    }


    //메일 발송 로직 분리`
    private void sendVerificationEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendEmail); //보내는사람
        message.setTo(email); //받는사람
        message.setSubject("인증 메일 테스트"); //제목
        message.setText(code); // 본문
        mailSender.send(message);
    }

}
