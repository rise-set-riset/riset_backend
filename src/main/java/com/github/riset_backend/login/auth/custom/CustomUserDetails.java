//package com.github.riset_backend.login.auth.custom;
//
//
//import com.github.riset_backend.login.employee.entity.Employee;
//import lombok.Builder;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//
//@Getter
//@Builder
//public class CustomUserDetails implements UserDetails {
//    private Employee employee;
//
//
//    public CustomUserDetails(Employee employee) {
//        this.employee = employee;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collections = new ArrayList<>();
////        collections.add(() -> String.valueOf(employee.getRoles().getRolesName()));
//        return collections;
//    }
//
//    @Override
//    public String getPassword() {
//        return employee.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return employee.getEmployeeId();
//    }
//
//    /* 계정 만료 여부
//     * true :  만료 안됨
//     * false : 만료
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    /* 계정 잠김 여부
//     * true : 잠기지 않음
//     * false : 잠김
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    /* 비밀번호 만료 여부
//     * true : 만료 안 됨
//     * false : 만료
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    /* 사용자 활성화 여부
//     * true : 활성화 됨
//     * false : 활성화 안 됨
//     */
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
