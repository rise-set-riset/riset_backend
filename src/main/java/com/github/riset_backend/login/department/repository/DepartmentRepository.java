package com.github.riset_backend.login.department.repository;

import com.github.riset_backend.login.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByDeptName(String deptName);

    List<Department> findAllByCompany_CompanyNo(Long CompanyNo);
}
