package com.github.riset_backend.global.config.auth;


import com.github.riset_backend.global.config.auth.custom.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserDetailsServiceImpl customUserDetailService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private Long accessExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private Long refreshExpirationTime;


    /**
     * Access 토큰 생성
     */
    public String generateToken(String id, Long expireTime) {
        Claims claims = Jwts.claims().setSubject(id);

        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createAccessToken(String id){
        return generateToken(id, accessExpirationTime);
    }

    public String createRefreshToken(String id){
        return generateToken(id, refreshExpirationTime);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.parseClaims(token));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        return authentication;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public String parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public void setRole(String accessToken, String role){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
        claims.put("roles", role);
        log.info("claims: {}", claims);
        Jwts.builder()
                .setClaims(claims)
                .compact();
    }

    public String getIdByToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.isEmpty() ? null : claims.get("sub", String.class);
    }
}