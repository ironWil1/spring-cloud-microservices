package com.microservices.deposit.rest.dto;

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

}
