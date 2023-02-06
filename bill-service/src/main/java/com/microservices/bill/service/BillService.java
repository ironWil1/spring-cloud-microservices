package com.microservices.bill.service;

import com.microservices.bill.controller.dto.BillResponseDto;
import com.microservices.bill.entity.Bill;
import com.microservices.bill.exceptions.BillNotFoundException;
import com.microservices.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBillById(Long billId) {
        Bill bill = null;
        try{
            bill = billRepository.findById(billId).get();
            //                .orElseThrow(() -> new BillNotFoundException("Счет с ID: " + billId + " не найден!"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Какие то проблемы с поиском счета!");
        }
        return bill;
    }

    public Long createBill(Long accountId, BigDecimal amount,
                           Boolean isDefault, Boolean overdraftEnabled) {
        Bill bill = Bill.builder()
                .accountId(accountId)
                .amount(amount)
                .isDefault(isDefault)
                .creationDate(OffsetDateTime.now())
                .overdraftEnabled(overdraftEnabled)
                .build();
        return billRepository.save(bill).getBillId();
    }

    public Bill updateBill(Long billId, Long accountId, BigDecimal amount,
                           Boolean isDefault, Boolean overdraftEnabled) {
        Bill bill = Bill.builder()
                .billId(billId)
                .accountId(accountId)
                .amount(amount)
                .isDefault(isDefault)
                .overdraftEnabled(overdraftEnabled)
                .build();
        return billRepository.save(bill);
    }

    public Bill deleteBill(Long billId) {
        Bill deletedBill = getBillById(billId);
        billRepository.deleteById(billId);
        return deletedBill;
    }

    public List<Bill> getBillByAccountId(Long accountId) {
        return billRepository.getBillsByAccountId(accountId);
    }
}
