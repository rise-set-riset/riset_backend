package com.github.riset_backend.login.jobGrade.controller;

import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import com.github.riset_backend.login.jobGrade.service.JobGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobGrade")
public class JobGradeController {

    private final JobGradeService jobGradeService;

    @GetMapping()
    public ResponseEntity< List<JobGrade>> getJobGrade () {
        List<JobGrade> jobGrade = jobGradeService.getJobGrade();
        return ResponseEntity.ok(jobGrade);
    }

}
