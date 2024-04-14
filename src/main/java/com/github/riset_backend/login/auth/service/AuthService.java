package com.github.riset_backend.login.auth.service;


//import com.github.riset_backend.global.config.auth.JwtTokenProvider;

import com.github.riset_backend.global.config.auth.JwtTokenProvider;
import com.github.riset_backend.global.config.auth.filter.JwtAuthenticationFilter;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.auth.dto.RequestLoginDto;
import com.github.riset_backend.login.auth.dto.RequestSignUpDto;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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

            response.put("message", "로그인 되었습니다");
            response.put("token_type", "Bearer");
            response.put("access_token", accessToken);
            response.put("refresh_token", refreshToken);

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

    public ResponseEntity<String> checkId(String id) {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(id);

        if(employee.isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }
        return ResponseEntity.ok().body("사용할 수 있는 아이디입니다.");
    }
}
