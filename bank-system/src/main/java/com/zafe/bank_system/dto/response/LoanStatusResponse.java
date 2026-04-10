package com.zafe.bank_system.dto.response;

import com.zafe.bank_system.enums.LoanStatus;
import com.zafe.bank_system.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Summarises the current state of a loan for "check loan status" queries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusResponse {

    private Long loanId;
    private LoanType loanType;
    private LoanStatus status;
    private BigDecimal principalAmount;
    private BigDecimal remainingBalance;
    private int totalInstallments;
    private long paidInstallments;
    private long pendingInstallments;
}
