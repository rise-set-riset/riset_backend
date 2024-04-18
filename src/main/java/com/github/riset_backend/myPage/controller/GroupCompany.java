package com.github.riset_backend.myPage.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.myPage.dto.MyPageImageUploadDto;
import com.github.riset_backend.myPage.dto.UpdateCompanyAddress;
import com.github.riset_backend.myPage.service.MyPageService;
import com.github.riset_backend.myPage.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class GroupCompany {


    public UploadImageService uploadImageService;
    public MyPageService myPageService;


    public GroupCompany(UploadImageService uploadImageService, MyPageService myPageService) {
        this.uploadImageService = uploadImageService;
        this.myPageService = myPageService;
    }

    //조직 업로드와 수정
    @PatchMapping("/group/companyUpdateImage")
    public MyPageImageUploadDto companyUpdateImage(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("file") MultipartFile file) throws IOException {

        MyPageImageUploadDto answer = uploadImageService.updateCompanyImage(user, file);
        return ResponseEntity.status(HttpStatus.OK).body(answer).getBody();
    }

    @PostMapping("/group/address")
    public ResponseEntity<String> companyAddress(@AuthenticationPrincipal CustomUserDetails user, @RequestBody UpdateCompanyAddress companyAddress) {
        String answer = myPageService.updateCompanyAddress(user, companyAddress);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
}
