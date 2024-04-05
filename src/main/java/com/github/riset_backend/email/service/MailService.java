package com.github.riset_backend.email.service;

import com.github.riset_backend.email.entity.Email;
import com.github.riset_backend.email.repository.EmailRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
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


import static com.github.riset_backend.global.config.exception.ErrorCode.*;

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
    public void answerMail(String email) {
        //토큰에서 회사 id 뽑아서
//        Long companyId = companyGetId(token);


        //todo : 이메일 요청하는 사람이 유효한지 확인 / admin 이여야합니다


        //todo : 받은 이메일이 실제 회원인지 확인해야합니다

        //메일 보내기
        try {
            String code = RandomCodeGenerator.generateCode() + 1;
            // 이메일 전송
            sendVerificationEmail(email, code);

            //todo: 이메일 전송된 회원의 회원의 아이디값을 넣을지 말지 고민중, 이메일 코드는 확인하고 바로 삭제하니까 상관없으려나...
            Email emailEntity = Email.builder().code(code).build();
            emailRepository.save(emailEntity);//db 저장
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }


    // code 확인 회원등록, 직원이 코드를 입력하는것이기에 toke 은 직원유저
    public Long checkCompanyCode(String code) {
        //todo : 에러코드 변경해야함

        String numbersOnly = code.replaceAll("[^0-9]", ""); // 그리고 숫자만 추출 = 회사아이디임
        log.info("Check company code {}", numbersOnly);
        //회사 찾음
        Company company = companyRepository.findById(Long.valueOf(numbersOnly)).orElseThrow(() -> new BusinessException(NOT_USER));
        //직원 찾음
        Employee employee = employeeRepository.findByEmployeeNo(7L).orElseThrow(() -> new BusinessException(NOT_USER));
        //코드비교
        Email email = emailRepository.findByCode(code).orElseThrow(() -> new BusinessException(NOT_USER, "코드가 옳지 않아요~"));

        //todo: 회사에 직원정보를 저장해야합니다.


        //todo: 직원조회시 회사를 알아야하고, 회사조회시 직원도 알아야합니다.

        employeeRepository.save(employee); // 직원 정보 저장


        return Long.parseLong(numbersOnly); //회사아이디 return
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

    //회사 id 가져오기
    public Long companyGetId(String token) {
        //todo : JWT, 시크릿키, 값을 몰라서 임시로 해놓음 수정,
        String key = "qwe";
        //todo : 로그인 구현 완료되면 외부로 정리하기
        String jwtToken = token.substring(7);
        System.out.println("JWT Token: " + jwtToken);
        // 시크릿 키를 사용하여 토큰을 디코딩하여 클레임을 얻습니다.
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
        System.out.println("Claims: " + claims);
        // 클레임에서 companyId를 추출합니다.
        Integer companyId = (Integer) claims.get("companyId");
        String employeeId = (String) claims.get("email");

        //todo : 값 정보를 가져오는 로직도 전부 정리하기,
        //회사정보 가져옴
        Company company = companyRepository.findById(Long.valueOf(companyId)).orElseThrow(() -> new BusinessException(NOT_FOUND_CATEGORY, "회사 정보 없음"));


        return company.getCompanyNo();
    }


    //email 가져오기
    public String getEmail(String token) {
        //todo : JWT, 시크릿키, 값을 몰라서 임시로 해놓음 수정,
        String key = "qwe";
        //todo : 로그인 구현 완료되면 외부로 정리하기
        String jwtToken = token.substring(7);
        System.out.println("JWT Token: " + jwtToken);
        // 시크릿 키를 사용하여 토큰을 디코딩하여 클레임을 얻습니다.
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
        System.out.println("Claims: " + claims);
        // 클레임에서 companyId를 추출합니다.
        Integer companyId = (Integer) claims.get("companyId");
        String employeeId = (String) claims.get("email");

        //todo : 값 정보를 가져오는 로직도 전부 정리하기,
        //회사정보 가져옴
        Company company = companyRepository.findById(Long.valueOf(companyId)).orElseThrow(() -> new BusinessException(NOT_FOUND_CATEGORY, "회사 정보 없음"));


        return employeeId;
    }
}
