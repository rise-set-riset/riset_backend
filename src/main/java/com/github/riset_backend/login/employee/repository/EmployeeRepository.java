package com.github.riset_backend.login.employee.repository;


import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeId(String id);

    Optional<Employee> findByEmployeeId(String id);


    Optional<Employee> findById(Long id);

    Optional<Employee> findByEmployeeNo(Long id);

    Optional<Employee> findByName(String name);

    Optional<List<Employee>> findAllByRoles(Role role);

    List<Employee> findAllByCompany_CompanyNo(Long companyNo);
}
