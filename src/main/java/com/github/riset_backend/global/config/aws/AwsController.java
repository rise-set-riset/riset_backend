package com.github.riset_backend.global.config.aws;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsController {

    @GetMapping("/health")
    public ResponseEntity getLoadBalanceHealth (HttpServletResponse response) {
        return ResponseEntity.ok("Sucess Heatlth check");
    }
}
