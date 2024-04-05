package com.github.riset_backend.login.employee.controller;


import com.github.riset_backend.login.employee.PresetService;
import com.github.riset_backend.login.employee.dto.PresetAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preset")
@RequiredArgsConstructor
public class PresetController {
    private final PresetService presetService;

    @PostMapping("/admin")
    public ResponseEntity<String> presetAdmin(@RequestBody PresetAdminDto adminDto){

       return presetService.admin(adminDto);
    }


    @PostMapping("/employee")
    public String presetEmployee(){

        return "SUCCESS";
    }
}
