package com.github.riset_backend.login.auth.service;


//import com.github.riset_backend.global.config.auth.JwtTokenProvider;

import com.github.riset_backend.global.config.auth.JwtTokenProvider;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.auth.filter.JwtAuthenticationFilter;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;

import com.github.riset_backend.login.auth.dto.*;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.manageCompany.service.RandomCodeGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${spring.mail.username}")
    private String sendEmail;

    @Transactional
    public ResponseEntity<String> employeeSignup(RequestSignUpDto requestSignUpDto) {
        if (employeeRepository.existsByEmployeeId(requestSignUpDto.id())) {
            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
        }

        Employee employee = Employee.builder()
                .employeeId(requestSignUpDto.id())
                .password(passwordEncoder.encode(requestSignUpDto.password()))
                .name(requestSignUpDto.name())
                .phoneNumber(requestSignUpDto.phone())
                .build();

        employeeRepository.save(employee);

        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> employeeLogin(RequestLoginDto requestLoginDto, HttpServletResponse httpServletResponse) {
        Employee employee = employeeRepository.findByEmployeeId(requestLoginDto.id()).isEmpty() ? null : employeeRepository.findByEmployeeId(requestLoginDto.id()).get();

        if (employee == null) {
            throw (new BusinessException(ErrorCode.CHECK_LOGIN_ID_OR_PASSWORD));
        }

        Map<String, String> response = new HashMap<>();

        String accessToken = "";
        String refreshToken = "";
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestLoginDto.id(), requestLoginDto.password());
//            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (redisTemplate.opsForValue().get("logout: " + requestLoginDto.id()) != null) {
                redisTemplate.delete("logout: " + requestLoginDto.id());
            }

            if (!passwordEncoder.matches(requestLoginDto.password(), employee.getPassword())) {
                throw new BusinessException(ErrorCode.NOT_EQUAL_PASSWORD);
            }


            accessToken = jwtTokenProvider.createAccessToken(authenticationToken.getName());
            refreshToken = jwtTokenProvider.createRefreshToken(authenticationToken.getName());

            log.info("accessToken: {}", accessToken);
            log.info("refreshToken: {}", refreshToken);

            httpServletResponse.addHeader("Authorization", accessToken);
            httpServletResponse.addCookie(new Cookie("refresh_token", refreshToken));

//            redisTemplate.opsForValue().set(requestLoginDto.id(), accessToken, Duration.ofHours(1L));
            redisTemplate.opsForValue().set("RF: " + requestLoginDto.id(), refreshToken, Duration.ofHours(3L));

            if(employee.getRoles() != null){
                response.put("isAuth", "true");
            } else {
                response.put("isAuth", "false");
            }

            response.put("message", "로그인 되었습니다");
            response.put("token_type", "Bearer");
            response.put("access_token", accessToken);
            response.put("refresh_token", refreshToken);
            response.put("userId", employee.getEmployeeNo().toString());

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            response.put("message", "잘못된 자격 증명입니다");
            response.put("http_status", HttpStatus.UNAUTHORIZED.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }


    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String accessToken = jwtAuthenticationFilter.resolveToken(request);

        if (jwtTokenProvider.validateToken(accessToken)) {
            return ResponseEntity.badRequest().body(new BusinessException(ErrorCode.VALID_ACCESS_TOKEN));
        }

        String refreshToken = findRefreshTokenCookie(request);
        String name = jwtTokenProvider.getIdByToken(refreshToken);
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(name).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER)));

        if (!refreshToken.equals(redisTemplate.opsForValue().get("RF: " + name))) {
            return ResponseEntity.badRequest().body(new BusinessException(ErrorCode.INCORRECT_REFRESH_TOKEN));
        }

        if(!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.badRequest().body(new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));
        }

        accessToken = jwtTokenProvider.createAccessToken(name);

        Role role = employee.get().getRoles();
        jwtTokenProvider.setRole(accessToken ,role.getType());

        httpServletResponse.addHeader("Authorization", accessToken);

        Map<String, String> response = new HashMap<>();
        response.put("access_token", accessToken);
        response.put("http_status", HttpStatus.CREATED.toString());

        return ResponseEntity.ok(response);
    }

    public String findRefreshTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }

                if (cookie.getValue() == null) {
                    throw new BusinessException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
                }
                return cookie.getValue();
            }

        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_COOKIE);
        }
        return null;
    }

    public boolean checkId(RequestCheckIdDto requestCheckIdDto) {
        if (employeeRepository.existsByEmployeeId(requestCheckIdDto.id())) {
            return true;
        }
        return false;
    }

    public ResponseEntity<?> findId(FindIdRequestDto findIdRequestDto) {
        Optional<Employee> employee = employeeRepository.findByName(findIdRequestDto.name());

        if(employee.isPresent()){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sendEmail); //보내는사람
            message.setTo(findIdRequestDto.email()); //받는사람
            message.setSubject(findIdRequestDto.name() + "님의 아이디 안내 이메일입니다."); //제목
            message.setText("안녕하세요. 아이디 안내 관련 이메일입니다." + "[" + findIdRequestDto.name() + "]" + "님의 아이디는 " + employee.get().getEmployeeId() + "입니다."); // 본문
            mailSender.send(message);
            return ResponseEntity.ok().body("입력하신 이메일로 아이디를 발송하였습니다.");
        } else{
            return ResponseEntity.badRequest().body("알 수 없는 요청입니다.");
        }

    }

    public ResponseEntity<?> findPassword(FindPasswordRequestDto findPasswordRequestDto) {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(findPasswordRequestDto.id());
        employee.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        String code = RandomCodeGenerator.generateCode() + "!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendEmail); //보내는사람
        message.setTo(findPasswordRequestDto.email()); //받는사람
        message.setSubject(findPasswordRequestDto.id() + "님의 임시비밀번호 안내 이메일입니다."); //제목
        message.setText("안녕하세요. 임시비밀번호 안내 관련 이메일입니다." + "[" + findPasswordRequestDto.id() + "]" +"님의 임시 비밀번호는 "
                + code + " 입니다."); // 본문
        mailSender.send(message);



        employee.get().setPassword(passwordEncoder.encode(code));
        employeeRepository.save(employee.get());


        return ResponseEntity.ok().body("입력하신 이메일로 임시 비밀번호를 발송하였습니다.");
    }

    public ResponseEntity<List<?>> getUserInfo() {
        List<Employee> employees = employeeRepository.findAllByRoles(Role.ROLE_EMPLOYEE).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        List<Employee> employeeList = employeeRepository.findAllByRoles(Role.ROLE_ADMIN).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        List<AllUserResponseDto> allUserResponseDtos = new ArrayList<>();


        employees.forEach(employee -> {
            if(employee.getMyImage() == null) {
                AllUserResponseDto dto = AllUserResponseDto.builder()
                        .name(employee.getName())
                        .employeeId(employee.getEmployeeNo())
                        .build();
                allUserResponseDtos.add(dto);
            } else{
                AllUserResponseDto dto = AllUserResponseDto.builder()
                        .name(employee.getName())
                        .employeeId(employee.getEmployeeNo())
                        .profileId(employee.getMyImage().getMyImageId())
                        .profileName(employee.getMyImage().getFileName())
                        .profilePath(employee.getMyImage().getFilePath())
                        .build();
                allUserResponseDtos.add(dto);
            }

        });

        employeeList.forEach(employee -> {
            if(employee.getMyImage() == null) {
                AllUserResponseDto dto = AllUserResponseDto.builder()
                        .name(employee.getName())
                        .employeeId(employee.getEmployeeNo())
                        .build();
                allUserResponseDtos.add(dto);
            } else {
                AllUserResponseDto dto = AllUserResponseDto.builder()
                        .name(employee.getName())
                        .employeeId(employee.getEmployeeNo())
                        .profileId(employee.getMyImage().getMyImageId())
                        .profileName(employee.getMyImage().getFileName())
                        .profilePath(employee.getMyImage().getFilePath())
                        .build();
                allUserResponseDtos.add(dto);
            }
            });

        return ResponseEntity.ok().body(allUserResponseDtos);
    }
}
