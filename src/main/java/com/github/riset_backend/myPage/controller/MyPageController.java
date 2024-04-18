package com.github.riset_backend.myPage.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.myPage.dto.ModifyUserPasswordRequest;
import com.github.riset_backend.myPage.dto.MyPageInfo;
import com.github.riset_backend.myPage.dto.UserUpdateDto;
import com.github.riset_backend.myPage.service.MyPageService;
import com.github.riset_backend.myPage.service.UploadImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
@Tag(name = "마이페이지 이미지 / 회원 정보 등록,수정,삭제 api", description = "마이페이지 이미지/회원정보 등록, 수정, 삭제 api")
public class MyPageController {

    private final MyPageService myPageService;
    private final UploadImageService uploadImageService;


    @GetMapping("/get")
    @Operation(summary = "내 정보를 가져오는 api", description = "내 정보를 볼 수 있습니다")
    public ResponseEntity<MyPageInfo> getId(@AuthenticationPrincipal CustomUserDetails user) {
        MyPageInfo answer = myPageService.MyPageALL(user);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
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

    //패스워드 수정
    @PatchMapping("/passwordUpdate")
    public ResponseEntity<String> passwordUpdate(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ModifyUserPasswordRequest request) {
        String answer = myPageService.updatePassword(request, user);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    //유저 업데이트
    @PatchMapping("/userUpdate")
    public ResponseEntity<String> userUpdate(@AuthenticationPrincipal CustomUserDetails user, @RequestBody UserUpdateDto request) {
        String answer = myPageService.updateUser(request, user);
        if (answer.equals("수정완료")) {
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(answer);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return myPageService.deleteUser(customUserDetails);
    }

}