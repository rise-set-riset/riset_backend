//package com.github.riset_backend.login.auth.service;
//
//
////import com.github.riset_backend.global.config.auth.JwtTokenProvider;
//import com.github.riset_backend.global.config.exception.BusinessException;
//import com.github.riset_backend.global.config.exception.ErrorCode;
//import com.github.riset_backend.login.auth.dto.RequestLoginDto;
//import com.github.riset_backend.login.auth.dto.RequestSignUpDto;
//import com.github.riset_backend.login.auth.dto.TokenDto;
//import com.github.riset_backend.login.employee.entity.Employee;
//import com.github.riset_backend.login.employee.repository.EmployeeRepository;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class AuthService {
//    private final EmployeeRepository employeeRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final RedisTemplate<String, String> redisTemplate;
//
//    @Transactional
//    public ResponseEntity<String> employeeSignup(RequestSignUpDto requestSignUpDto) {
//        if (employeeRepository.existsByEmployeeId(requestSignUpDto.id())) {
//            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
//        }
//
//        Employee employee = Employee.builder()
//                .employeeId(requestSignUpDto.id())
//                .password(passwordEncoder.encode(requestSignUpDto.password()))
//                .name(requestSignUpDto.name())
//                .phoneNumber(requestSignUpDto.phone())
//                .build();
//
//        employeeRepository.save(employee);
//
//        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
//    }
//
//    @Transactional
//    public ResponseEntity<?> employeeLogin(RequestLoginDto requestLoginDto, HttpServletResponse httpServletResponse) {
//        Employee employee = employeeRepository.findByEmployeeId(requestLoginDto.id()).isEmpty() ? null : employeeRepository.findByEmployeeId(requestLoginDto.id()).get();
//
//        if (employee == null) {
//            throw (new BusinessException(ErrorCode.CHECK_LOGIN_ID_OR_PASSWORD));
//        }
//
//        Map<String, String> response = new HashMap<>();
//
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestLoginDto.id(), requestLoginDto.password());
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//            if (redisTemplate.opsForValue().get("logout: " + requestLoginDto.id()) != null) {
//                redisTemplate.delete("logout: " + requestLoginDto.id());
//            }
//
//            if(!passwordEncoder.matches(requestLoginDto.password(), employee.getPassword()))
//                throw new BusinessException(ErrorCode.NOT_EQUAL_PASSWORD);
//            TokenDto tokenDto = jwtTokenProvider.generateToken(authenticationToken);
//
//            redisTemplate.opsForValue().set(requestLoginDto.id(), tokenDto.getAccesstoken(), Duration.ofHours(1L));
//            redisTemplate.opsForValue().set("RF: " + requestLoginDto.id(), tokenDto.getRefreshtoken(), Duration.ofHours(3L));
//
//            response.put("http_status", HttpStatus.OK.toString());
//            response.put("message", "로그인 되었습니다");
//            response.put("token_type", "Bearer");
//            response.put("access_token", tokenDto.getAccesstoken());
//            response.put("refresh_token", tokenDto.getRefreshtoken());
//
//            return ResponseEntity.ok(response);
//
//        } catch (BadCredentialsException e) {
//            e.printStackTrace();
//            response.put("message", "잘못된 자격 증명입니다");
//            response.put("http_status", HttpStatus.UNAUTHORIZED.toString());
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
//        }
//    }
//}
