package com.microservices.bill.controller;

import com.microservices.bill.controller.dto.BillRequestDto;
import com.microservices.bill.controller.dto.BillResponseDto;
import com.microservices.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponseDto> getBill(@PathVariable Long billId) {

        BillResponseDto billResponseDto = new BillResponseDto(billService.getBillById(billId));
        return ResponseEntity.ok(billResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<Long> createBill(@RequestBody BillRequestDto dto) {
        return ResponseEntity.ok(
                billService.createBill(dto.getAccountId(), dto.getAmount(),
                        dto.getIsDefault(), dto.getOverdraftEnabled()));
    }

    @PutMapping("/{billId}")
    public ResponseEntity<BillResponseDto> updateBill(@PathVariable Long billId, @RequestBody BillRequestDto dto) {
        return ResponseEntity.ok(
                new BillResponseDto(billService.updateBill(billId, dto.getAccountId(), dto.getAmount(),
                        dto.getIsDefault(), dto.getOverdraftEnabled())));
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<BillResponseDto> deleteBill(@PathVariable Long billId) {
        return ResponseEntity.ok(new BillResponseDto(billService.deleteBill(billId)));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<BillResponseDto>> getBillByAccountId(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(billService.getBillByAccountId(accountId)
                .stream()
                .map(BillResponseDto::new)
                .collect(Collectors.toList()));
    }
}
