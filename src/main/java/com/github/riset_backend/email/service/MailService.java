package com.github.riset_backend.email.service;

import com.github.riset_backend.email.entity.Email;
import com.github.riset_backend.email.repository.EmailRepository;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.github.riset_backend.global.config.exception.ErrorCode.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;


    @Value("${spring.mail.username}")
    private String sendEmail;

    // 이메일 발송
    public void answerMail(String email, CustomUserDetails user) {

        //유저 확인
        Optional<Employee> userName = Optional.ofNullable(employeeRepository.findByEmployeeId(user.getUsername()).orElseThrow(() -> new BusinessException(NOT_USER, "유저가 없어요")));

        //회사 확인
        if (userName.get().getCompany().getCompanyNo() != null) {
            Long companyNo = userName.get().getCompany().getCompanyNo();
            try {
                String code = RandomCodeGenerator.generateCode() + companyNo;
                // 이메일 전송
                sendVerificationEmail(email, code);


                Email emailEntity = Email.builder()
                        .code(code)
                        .employeeId(Long.valueOf(userName.get().getEmployeeNo()))
                        .build();
                emailRepository.save(emailEntity);//db 저장
            } catch (MailException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // code 확인 회원등록, 직원이 코드를 입력하는것이기에 toke 은 직원유저
    public void CompanyCode(String code, CustomUserDetails user) {
        //직원 id
        try {
            // 이메일 리포지토리에서 코드를 사용하여 직원 ID를 가져옴
            Long employeeUser = emailRepository.findByCode(code)
                    .orElseThrow(() -> new BusinessException(NOT_USER, "이메일이 코드와 연관되어 있지 않습니다."))
                    .getEmployeeId();

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

            // 직원 정보를 저장
            employeeRepository.save(employee);
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 코드일 때 발생하는 예외 처리
            throw new BusinessException(NOT_USER, "잘못된 코드 형식입니다.");
        } catch (BusinessException e) {
            // 사용자 정의 비즈니스 예외 처리
            throw e;
        } catch (Exception e) {
            // 기타 예외 처리
            throw new BusinessException(NOT_USER, "직원 정보를 저장하는 동안 오류가 발생했습니다.", e);
        }
    }


    //메일 발송 로직 분리
    private void sendVerificationEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendEmail); //보내는사람
        message.setTo(email); //받는사람
        message.setSubject("인증 메일 테스트"); //제목
        message.setText(code + " 하세용"); // 본문
        mailSender.send(message);
    }

}
