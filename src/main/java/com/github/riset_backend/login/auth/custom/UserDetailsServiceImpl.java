package com.github.riset_backend.login.auth.custom;

import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        int start = id.indexOf("[Principal=") + "[Principal=".length();
        int end = id.indexOf(",", start);
        String result = id.substring(start, end);
        log.info("result: {}", result);

        Employee employee = employeeRepository.findByEmployeeId(result).orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_MEMBER));


        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        return new CustomUserDetails(employee);
    }
}

