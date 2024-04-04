//package com.github.riset_backend.login.auth.service;
//
//
//
//import com.github.riset_backend.global.config.exception.BusinessException;
//import com.github.riset_backend.global.config.exception.ErrorCode;
//import com.github.riset_backend.login.auth.custom.CustomUserDetails;
//import com.github.riset_backend.login.employee.entity.Employee;
//import com.github.riset_backend.login.employee.repository.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.sql.ast.tree.expression.Collation;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailService implements UserDetailsService {
//    private final EmployeeRepository employeeRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
//        Employee employee = !employeeRepository.findByEmployeeId(id).isPresent() ? null : employeeRepository.findByEmployeeId(id).get();
//
//        if (employee == null) {
//            return new CustomUserDetails(employee);
//        }
//        return null;
//    }
//}
