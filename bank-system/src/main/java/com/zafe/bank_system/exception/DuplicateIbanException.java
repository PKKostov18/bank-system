package com.zafe.bank_system.exception;

public class DuplicateIbanException extends RuntimeException {

    public DuplicateIbanException(String iban) {
        super("A bank account with IBAN " + iban + " already exists");
    }
}
