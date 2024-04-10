package com.github.riset_backend.login.jobGrade.entity;

import com.github.riset_backend.Settlement.dto.Rating;
import com.github.riset_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "job_grade")
public class JobGrade extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_no")
    private Long gradeNo;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

}
