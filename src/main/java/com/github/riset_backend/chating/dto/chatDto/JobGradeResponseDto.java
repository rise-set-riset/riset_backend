package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobGradeResponseDto {

    private Long jobGradeId;
    private Integer grade;

    public JobGradeResponseDto(JobGrade jobGrade) {
        this.jobGradeId = jobGrade.getGradeNo();
        this.grade = jobGrade.getGrade();
    }
}
