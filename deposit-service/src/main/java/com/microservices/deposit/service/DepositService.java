package com.microservices.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.deposit.controller.dto.DepositResponseDTO;
import com.microservices.deposit.entity.Deposit;
import com.microservices.deposit.exceptions.DepositServiceException;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.AccountServiceClient;
import com.microservices.deposit.rest.BillServiceClient;
import com.microservices.deposit.rest.dto.AccountResponseDTO;
import com.microservices.deposit.rest.dto.BillRequestDto;
import com.microservices.deposit.rest.dto.BillResponseDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    public static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    public static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";
    private DepositRepository depositRepository;

    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository depositRepository, AccountServiceClient accountServiceClient,
                          BillServiceClient billServiceClient, RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDTO deposit(Long accountId, Long billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Не указан ID аккаунта и счета");
        }

        if (billId != null) {
            BillResponseDto billResponseDto = billServiceClient.getBillById(billId).getBody();
            BillRequestDto billRequestDto = BillRequestDto.builder()
                    .accountId(billResponseDto.getAccountId())
                    .creationDate(billResponseDto.getCreationDate())
                    .overdraftEnabled(billResponseDto.getOverdraftEnabled())
                    .isDefault(billResponseDto.getIsDefault())
                    .amount(billResponseDto.getAmount().add(amount)).build();

            billServiceClient.update(billId,billRequestDto);

            AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(billResponseDto.getAccountId()).getBody();
            depositRepository.save(
                    Deposit.builder()
                    .amount(amount)
                    .billId(billId)
                    .creationDate(OffsetDateTime.now())
                    .email(accountResponseDTO.getEmail())
                    .build());

            return sendDepositResponseDtoToRabbitMQ(amount,accountResponseDTO.getEmail());
        }

        BillResponseDto defaultBill = getDefaultBill(accountId);
        BillRequestDto billRequestDto = createBillRequestDto(amount, defaultBill);
        billServiceClient.update(defaultBill.getBillId(), billRequestDto);
        AccountResponseDTO accountById = accountServiceClient.getAccountById(accountId).getBody();
        depositRepository.save(Deposit.builder()
                .amount(amount)
                .billId(defaultBill.getBillId())
                .creationDate(OffsetDateTime.now())
                .email(accountById.getEmail())
                .build());

        return sendDepositResponseDtoToRabbitMQ(amount,accountById.getEmail());

    }

    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId).getBody().
                stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() ->
                        new DepositServiceException("Не получилось найти Default bill для аккаунта с ID: " + accountId));
    }

    private BillRequestDto createBillRequestDto(BigDecimal amount, BillResponseDto dto) {
        return BillRequestDto.builder()
                .accountId(dto.getAccountId())
                .creationDate(dto.getCreationDate())
                .overdraftEnabled(dto.getOverdraftEnabled())
                .isDefault(dto.getIsDefault())
                .amount(dto.getAmount().add(amount)).build();
    }

    private DepositResponseDTO sendDepositResponseDtoToRabbitMQ(BigDecimal amount, String email) {

        DepositResponseDTO depositResponseDTO = new DepositResponseDTO(amount,email);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(depositResponseDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Не получилось направить сообщение в RabbitMQ");
        }

        return depositResponseDTO;
    }
}
