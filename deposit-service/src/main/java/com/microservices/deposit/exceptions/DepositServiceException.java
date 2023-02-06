package com.microservices.deposit.exceptions;

public class DepositServiceException extends RuntimeException {
    public DepositServiceException(String message) {
        super(message);
    }
}
