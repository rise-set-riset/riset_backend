package com.github.riset_backend.email.service;

import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

import java.util.Random;

public class RandomCodeGenerator {




    // 랜덤 코드 생성
    public static String generateCode() {

        // 랜덤 글자를 담을 StringBuilder 생성
        StringBuilder sb = new StringBuilder();
        // 랜덤 문자열에 포함될 문자들
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // Random 객체 생성
        Random random = new Random();

        // 길이가 6인 랜덤 글자 생성
        for (int i = 0; i < 6; i++) {
            // characters 문자열에서 랜덤으로 문자 선택하여 sb에 추가
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        // 생성된 랜덤 코드 반환
        return sb.toString();
    }

}


