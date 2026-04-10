package com.zafe.bank_system.exception;

import com.zafe.bank_system.enums.LoanType;

public class LoanTypeConfigNotFoundException extends RuntimeException {

    public LoanTypeConfigNotFoundException(LoanType loanType) {
        super("Loan type configuration not found for type: " + loanType);
    }
}
