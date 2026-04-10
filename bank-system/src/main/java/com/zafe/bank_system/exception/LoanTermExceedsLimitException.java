package com.zafe.bank_system.exception;

public class LoanTermExceedsLimitException extends RuntimeException {

    public LoanTermExceedsLimitException(int requested, int maximum) {
        super("Requested repayment term of " + requested + " months exceeds the maximum allowed term of " + maximum + " months");
    }
}
