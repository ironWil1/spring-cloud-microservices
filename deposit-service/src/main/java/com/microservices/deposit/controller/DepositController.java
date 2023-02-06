package com.microservices.deposit.controller;

import com.microservices.deposit.controller.dto.DepositRequestDTO;
import com.microservices.deposit.controller.dto.DepositResponseDTO;
import com.microservices.deposit.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposits")
    public ResponseEntity<DepositResponseDTO> createDeposit(@RequestBody DepositRequestDTO dto) {
        return ResponseEntity.ok(depositService.deposit(dto.getAccountId(), dto.getBillId(),
                dto.getAmount()));
    }
}
