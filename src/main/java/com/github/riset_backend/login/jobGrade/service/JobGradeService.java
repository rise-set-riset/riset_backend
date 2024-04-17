package com.github.riset_backend.login.jobGrade.service;
import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import com.github.riset_backend.login.jobGrade.repository.JobGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobGradeService {

    private final JobGradeRepository jobGradeRepository;

    public List<JobGrade> getJobGrade() {
        return jobGradeRepository.findAll();
    }
}
