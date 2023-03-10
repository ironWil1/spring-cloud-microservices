package com.microservices.bill.controller.dto;

import com.microservices.bill.entity.Bill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BillResponseDto {

    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnabled;

    public BillResponseDto(Bill bill) {
        billId = bill.getBillId();
        accountId = bill.getAccountId();
        isDefault = bill.getIsDefault();
        creationDate = bill.getCreationDate();
        overdraftEnabled = bill.getOverdraftEnabled();
        amount = bill.getAmount();
    }
}
