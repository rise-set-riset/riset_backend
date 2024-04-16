package com.github.riset_backend.global.config.auth;


import com.github.riset_backend.global.config.auth.filter.JwtAuthenticationFilter;
import com.github.riset_backend.global.config.oAuth.custom.CustomOAuth2UserService;
import com.github.riset_backend.global.config.oAuth.handler.MyAuthenticationFailureHandler;
import com.github.riset_backend.global.config.oAuth.handler.MyAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final MyAuthenticationSuccessHandler oAuth2LoginSuccessHandler;
    private final MyAuthenticationFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**");
    }


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((h) -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
//                .cors(c -> {
//                            CorsConfigurationSource source = request -> {
//                                // Cors 허용 패턴
//                                CorsConfiguration config = new CorsConfiguration();
//                                config.setAllowedOrigins(
//                                        List.of("*")
//                                );
//                                config.setAllowedMethods(
//                                        List.of("*")
//                                );
//                                return config;
//                            };
//                            c.configurationSource(source);
//                        }
//                )
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers(
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/auth/**",
                                        "/css/**",
                                        "/js/**",
                                        "/images/**",
                                        "/health",
                                        "/chat/**",
                                        "/ws-stomp",
                                        "/chatRoom/**",
                                        "/api/menus",
                                        "/oauth2/**").permitAll()
                                .requestMatchers("/company/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/code/*"))
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}