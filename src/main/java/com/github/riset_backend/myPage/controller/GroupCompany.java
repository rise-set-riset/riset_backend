package com.github.riset_backend.myPage.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.myPage.dto.MyPageImageUploadDto;
import com.github.riset_backend.myPage.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class GroupCompany {


    public UploadImageService uploadImageService;

    public GroupCompany(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    //조직 업로드와 수정
    @PatchMapping("/group/companyUpdateImage")
    public MyPageImageUploadDto companyUpdateImage(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("file") MultipartFile file) throws IOException {

        MyPageImageUploadDto answer = uploadImageService.updateCompanyImage(user, file);
        return ResponseEntity.status(HttpStatus.OK).body(answer).getBody();
    }
}
