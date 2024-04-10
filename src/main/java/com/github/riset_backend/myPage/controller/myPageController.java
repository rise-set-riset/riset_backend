package com.github.riset_backend.myPage.controller;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.myPage.dto.MyPageResponseDto;
import com.github.riset_backend.myPage.service.MyPageService;
import com.github.riset_backend.myPage.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
public class myPageController {

    private final MyPageService myPageService;
    private final UploadImageService uploadImageService;

    @GetMapping("/get")
    public MyPageResponseDto getId(@AuthenticationPrincipal CustomUserDetails user) {
        return myPageService.MyPageALL(user);
    }


    //이미지 업로드와 수정
    @PatchMapping("/updateImage")
    public void updateImage(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("file") MultipartFile file) throws IOException {
        uploadImageService.imageUpdate(user, file);
    }

    //이미지 삭제
    @DeleteMapping("/deleteImage")
    public void deleteImage(@AuthenticationPrincipal CustomUserDetails user) {
        uploadImageService.imageDelete(user);
    }

}
