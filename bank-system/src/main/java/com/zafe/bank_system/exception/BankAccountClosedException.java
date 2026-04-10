package com.zafe.bank_system.exception;

public class BankAccountClosedException extends RuntimeException {

    public BankAccountClosedException(Long accountId) {
        super("Bank account with id " + accountId + " is closed and cannot be used for this operation");
    }
}
