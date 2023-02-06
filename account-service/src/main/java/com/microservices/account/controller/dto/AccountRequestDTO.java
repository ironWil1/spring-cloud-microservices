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
public class AccountRequestDTO {

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime creationDate;

    private List<Long> billsIds;


}
