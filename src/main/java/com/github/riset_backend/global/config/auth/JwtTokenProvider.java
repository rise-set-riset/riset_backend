//package com.github.riset_backend.global.config.auth;
//
//
//
//import com.github.riset_backend.login.auth.dto.TokenDto;
//import com.github.riset_backend.login.auth.service.CustomUserDetailService;
//import io.jsonwebtoken.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Value;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class JwtTokenProvider {
//    private final RedisTemplate<String, String> redisTemplate;
//    private final CustomUserDetailService customUserDetailService;
//
//    @Value("${spring.jwt.secret}")
//    private String secretKey;
//
//    @Value("${spring.jwt.token.access-expiration-time}")
//    private long accessExpirationTime;
//
//    @Value("${spring.jwt.token.refresh-expiration-time}")
//    private long refreshExpirationTime;
//
//
//    /**
//     * Access 토큰 생성
//     */
//    public TokenDto generateToken(Authentication userId) {
//        // 권한 가져오기
//
//        long now = (new Date()).getTime();
//
//        // Access Token 생성
//        Date accessTokenExpiresIn = new Date(now + accessExpirationTime);
//
//        String accessToken = Jwts.builder()
//                .setSubject(String.valueOf(userId))
////                .claim("auth", authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//
//        // Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + refreshExpirationTime))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//
//        return TokenDto.builder()
//                .accesstoken(accessToken)
//                .refreshtoken(refreshToken)
//                .build();
//    }
//
//    /**
//     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
//     */
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = customUserDetailService.loadUserByUsername(parseClaims(token));
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
//        log.info("Authentication authorities: " + authentication.getAuthorities());
//        return authentication;
//    }
//
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
//        }
//        return false;
//    }
//
//    private String parseClaims(String accessToken) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(accessToken)
//                    .getBody()
//                    .getSubject();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims().getSubject();
//        }
//    }
//}
//
//
//
//
//
