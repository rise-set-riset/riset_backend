package com.github.riset_backend.myPage.dto;

import com.github.riset_backend.login.jobGrade.entity.JobGrade;

public record MyPageInfo(
        String image,
        String name,
        JobGrade jobGrade,
        String jobTitle,
        String joiningDate,
        String departmentName ,
        String position,
        String telNo,
        String address,
        int totalHoliday,
        int salary
) {
}

