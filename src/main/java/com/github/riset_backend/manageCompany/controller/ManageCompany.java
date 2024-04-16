package com.github.riset_backend.manageCompany.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/preset")
@RestController
@Tag(name = "지점관리, 등급지정, 휴가승인, 회사추가, 급여지정, 휴가일수 지정 api", description = "지점관리, 등급지정, 휴가승인, 회사추가, 급여지정, 휴가일수 지정 api")
public class ManageCompany {
}
