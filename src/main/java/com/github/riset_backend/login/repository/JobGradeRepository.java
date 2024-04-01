package com.github.riset_backend.login.repository;

import com.github.riset_backend.login.entity.JobGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobGradeRepository extends JpaRepository<JobGrade, Long> {
}
