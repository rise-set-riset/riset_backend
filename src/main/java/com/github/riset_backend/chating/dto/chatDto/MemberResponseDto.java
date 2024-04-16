package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.login.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberNo;
    private String memberId;
    private String memberName;
    private String job;
    private String position;
    private DepartResponseDto department;
    private JobGradeResponseDto jobGrade ;
    private ProfileImgResponseDto profileImg;

    public MemberResponseDto(Employee employee) {
        this.memberNo = employee.getEmployeeNo();
        this.memberId = employee.getEmployeeId();
        this.memberName = employee.getName();
        this.job = employee.getJob();
        this.position = employee.getPosition();
        this.department = employee.getDepartment() != null ?  new DepartResponseDto(employee.getDepartment()) : new DepartResponseDto();
        this.jobGrade = employee.getJobGrade() !=  null ? new JobGradeResponseDto(employee.getJobGrade()) : new JobGradeResponseDto();
        this.profileImg = employee.getMyImage() != null ? new ProfileImgResponseDto(employee.getMyImage()) : new ProfileImgResponseDto();
    }
}
