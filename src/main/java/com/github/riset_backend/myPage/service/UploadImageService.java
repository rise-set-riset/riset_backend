package com.github.riset_backend.myPage.service;


import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.entity.Role;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import com.github.riset_backend.myPage.dto.MyPageImageUploadDto;
import com.github.riset_backend.myPage.entity.MyImage;
import com.github.riset_backend.myPage.repository.MyPageRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class UploadImageService {


    private final MyPageRepository myPageRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    //조직 이미지 수정

    @Transactional
    public MyPageImageUploadDto updateCompanyImage(CustomUserDetails user, MultipartFile file) throws IOException {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Company company = companyRepository.findById(user.getEmployee().getCompany().getCompanyNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));


        //ROLE_ADMIN 유저만
        if (!employee.getRoles().equals(Role.ROLE_ADMIN)) {
            return null;
        }

        // 저장경로 설정
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        // 파일 이름 생성 (UUID + 원본 파일 이름)
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        // 파일 저장 경로 설정
        String filePath = projectPath + "/" + fileName;
        MyImage existingImage = company.getMyImage();

        try {
            if (existingImage != null) {
                Files.deleteIfExists(Paths.get(existingImage.getFilePath()));
                existingImage.updateImage(fileName, filePath);
            } else {
                existingImage = MyImage.builder()
                        .fileName(fileName)
                        .filePath(filePath)
                        .build();
            }

            file.transferTo(new File(filePath));

            MyImage savedImage = myPageRepository.save(existingImage);

            company.setMyImage(savedImage);
            companyRepository.save(company);
            return new MyPageImageUploadDto(savedImage.getFileName(), savedImage.getFilePath());
        } catch (Exception e) {
            throw new IOException();
        }
    }


    // 유저 이미지 업로드 및 수정
    public MyPageImageUploadDto imageUpdate(CustomUserDetails user, MultipartFile file) throws IOException {

        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));


        // 저장경로 설정
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        // 파일 이름 생성 (UUID + 원본 파일 이름)
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        // 파일 저장 경로 설정
        String filePath = projectPath + "/" + fileName;
        MyImage existingImage = employee.getMyImage();
        if (existingImage != null) {
            Files.deleteIfExists(Paths.get(existingImage.getFilePath()));
            existingImage.updateImage(fileName, filePath);
        } else {
            existingImage = MyImage.builder()
                    .fileName(fileName)
                    .filePath(filePath)
                    .build();
        }

        file.transferTo(new File(filePath));

        MyImage savedImage = myPageRepository.save(existingImage);

        employee.setMyImage(savedImage);
        employeeRepository.save(employee);
        return new MyPageImageUploadDto(savedImage.getFileName(), savedImage.getFilePath());
    }

    //삭제
    @Transactional
    public void imageDelete(CustomUserDetails user) {
        Employee employee = employeeRepository.findById(user.getEmployee().getEmployeeNo()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));
        MyImage myImage = myPageRepository.findById(employee.getMyImage().getMyImageId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_USER));

        if (myImage != null) {
            try {
                Files.deleteIfExists(Paths.get(myImage.getFilePath()));
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.UPLOAD_ERROR_IMAGE);
            }
        }
        myPageRepository.deleteById(employee.getMyImage().getMyImageId());
        employee.setMyImage(null);
        employeeRepository.save(employee);


    }

}
