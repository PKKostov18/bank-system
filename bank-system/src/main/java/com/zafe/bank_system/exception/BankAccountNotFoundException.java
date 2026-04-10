package com.zafe.bank_system.exception;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(Long id) {
        super("Bank account not found with id: " + id);
    }

    public BankAccountNotFoundException(String iban) {
        super("Bank account not found with IBAN: " + iban);
    }
}
