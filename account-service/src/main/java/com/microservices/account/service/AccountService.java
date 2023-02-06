package com.microservices.account.service;

import com.microservices.account.entity.Account;
import com.microservices.account.exceptions.AccountNotFoundException;
import com.microservices.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById (Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Аккаунт с ID " + accountId + " не был найден!"));
    }

    public Long createAccount (String name, String email,
                               String phone, List<Long> bills) {
        Account account = Account.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .creationDate(OffsetDateTime.now())
                .billsIds(bills).build();
        return accountRepository.save(account).getAccountID();
    }

    public Account updateAccount(Long accountId, String name,
                                 String email, String phone, List<Long> bills) {
        Account account = Account.builder()
                .accountID(accountId)
                .name(name)
                .phone(phone)
                .email(email)
                .billsIds(bills).build();
        return accountRepository.save(account);
    }

    public Account deleteAccount(Long accountId) {
        Account deletedAccount = getAccountById(accountId);
        accountRepository.deleteById(accountId);
        return deletedAccount;
    }
}
