package com.zafe.bank_system.exception;

public class InstallmentNotFoundException extends RuntimeException {

    public InstallmentNotFoundException(Long id) {
        super("Installment not found with id: " + id);
    }

    public InstallmentNotFoundException(Long loanId, Integer installmentNumber) {
        super("Installment #" + installmentNumber + " not found for loan id: " + loanId);
    }
}
