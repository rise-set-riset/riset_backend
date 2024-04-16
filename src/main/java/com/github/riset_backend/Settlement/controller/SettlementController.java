package com.github.riset_backend.Settlement.controller;

import com.github.riset_backend.Settlement.dto.SettlementServiceResponse;
import com.github.riset_backend.Settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settlement")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/settlements")
    public ResponseEntity<SettlementServiceResponse> getSettlements(){

        SettlementServiceResponse response = settlementService.getSettlementService(0, 15);

        return ResponseEntity.ok(response);
    }

}
