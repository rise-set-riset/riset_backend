package com.github.riset_backend.login.jobGrade.repository;

import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobGradeRepository extends JpaRepository<JobGrade, Long> {

    Optional<JobGrade> findByGradeNo(Long id);
}
