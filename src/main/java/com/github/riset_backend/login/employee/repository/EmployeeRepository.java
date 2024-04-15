package com.github.riset_backend.login.employee.repository;


import com.github.riset_backend.login.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeId(String id);

    Optional<Employee> findByEmployeeId(String id);


    Optional<Employee> findById(Long id);

    Optional<Employee> findByEmployeeNo(Long id);
}
