package com.zafe.bank_system.exception;

public class InstallmentAlreadyPaidException extends RuntimeException {

    public InstallmentAlreadyPaidException(Long installmentId) {
        super("Installment with id " + installmentId + " has already been paid");
    }
}
