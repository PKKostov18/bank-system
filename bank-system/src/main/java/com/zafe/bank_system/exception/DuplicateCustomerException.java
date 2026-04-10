package com.zafe.bank_system.exception;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String fieldName, String fieldValue) {
        super("Customer already exists with " + fieldName + ": " + fieldValue);
    }
}
