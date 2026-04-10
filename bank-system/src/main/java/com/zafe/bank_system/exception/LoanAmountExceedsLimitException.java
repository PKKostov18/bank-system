package com.zafe.bank_system.exception;

import java.math.BigDecimal;

public class LoanAmountExceedsLimitException extends RuntimeException {

    public LoanAmountExceedsLimitException(BigDecimal requested, BigDecimal maximum) {
        super("Requested loan amount " + requested + " exceeds the maximum allowed amount of " + maximum);
    }
}
