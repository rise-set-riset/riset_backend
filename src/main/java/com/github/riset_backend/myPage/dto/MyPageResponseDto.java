package com.github.riset_backend.myPage.dto;

public record MyPageResponseDto(
        String userId,   //아이디
        String birth,    //생일
        String email,    //이메일
        String telNo,    //전화번호
        String zipCOde,  // 주소
        String departmentName,
        String position, //직급
        String JobTitle,  // 직무
        String image
) {
}
