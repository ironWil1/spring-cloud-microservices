package com.microservices.account.controller.dto;

import com.microservices.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {

    private Long accountID;

    private String name;

    private String email;

    private String phone;

    private List<Long> billsIds;

    private OffsetDateTime creationDate;
    public AccountResponseDTO(Account account) {
        this.accountID = account.getAccountID();
        this.name = account.getName();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.creationDate = account.getCreationDate();
        this.billsIds = account.getBillsIds();
    }
}
