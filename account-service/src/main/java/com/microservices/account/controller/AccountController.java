package com.microservices.account.controller;

import com.microservices.account.controller.dto.AccountRequestDTO;
import com.microservices.account.controller.dto.AccountResponseDTO;
import com.microservices.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(
            @PathVariable Long accountId) {
        return ResponseEntity.ok(new AccountResponseDTO(accountService.getAccountById(accountId)));
    }

    @PostMapping("/")
    public ResponseEntity<Long> createAccount(@RequestBody AccountRequestDTO dto) {
        return ResponseEntity.ok(accountService.createAccount(dto.getName(), dto.getEmail(),
                dto.getPhone(), dto.getBillsIds()));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountId,
                                                            @RequestBody AccountRequestDTO dto) {
        return ResponseEntity.ok(new AccountResponseDTO(accountService.updateAccount(accountId, dto.getName(),
                dto.getEmail(), dto.getPhone(), dto.getBillsIds())));
    }

    @DeleteMapping ("/{accountId}")
    public ResponseEntity<AccountResponseDTO> deleteAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(new AccountResponseDTO(accountService.deleteAccount(accountId)));
    }
}
