package com.github.riset_backend.global.config.oAuth.custom;

import com.github.riset_backend.login.auth.service.AuthService;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final EmployeeRepository employeeRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        // OAuth2UserService를 사용하여 OAuth2User 정보를 가져온다.
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // 클라이언트 등록 ID(google, naver, kakao)와 사용자 이름 속성을 가져온다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


        // OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 OAuth2Attribute 객체를 만든다.
        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // OAuth2Attribute의 속성값들을 Map으로 반환 받는다.
        Map<String, Object> employeeAttribute = oAuth2Attribute.convertToMap();
        

        // 사용자 email(또는 id) 정보를 가져온다.
        String email = (String) employeeAttribute.get("email");
        // 이메일로 가입된 회원인지 조회한다.
        Optional<Employee> employee = employeeRepository.findByEmployeeId(email);

        if (employee.isEmpty()) {
            // 회원이 존재하지 않을경우, memberAttribute의 exist 값을 false로 넣어준다.
            employeeAttribute.put("exist", false);
            // 회원의 권한(회원이 존재하지 않으므로 기본권한인 ROLE_USER를 넣어준다), 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_EMPLOYEE.toString())),
                    employeeAttribute, "email");
        } else {
            // 회원이 존재할경우, memberAttribute의 exist 값을 true로 넣어준다.
            employeeAttribute.put("exist", true);
            // 회원의 권한과, 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(employee.get().getRoles().toString())),
                    employeeAttribute, "email");
        }
    }
}