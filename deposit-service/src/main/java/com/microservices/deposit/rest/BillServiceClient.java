package com.microservices.deposit.rest;

import com.microservices.deposit.rest.dto.BillRequestDto;
import com.microservices.deposit.rest.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @GetMapping("bills/{billId}")
    ResponseEntity<BillResponseDto> getBillById(@PathVariable("billId") Long billId);

    @PutMapping("bills/{billId}")
    void update(@PathVariable("billId") Long billId, @RequestBody BillRequestDto billRequestDto);

    @GetMapping("bills/account/{accountId}")
    ResponseEntity<List<BillResponseDto>> getBillsByAccountId(@PathVariable("accountId") Long accountId);
}
