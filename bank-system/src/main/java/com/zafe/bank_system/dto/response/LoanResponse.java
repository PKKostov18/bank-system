package com.zafe.bank_system.dto.response;

import com.zafe.bank_system.enums.LoanStatus;
import com.zafe.bank_system.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {

    private Long id;
    private Long customerId;
    private LoanType loanType;
    private BigDecimal principalAmount;
    private Integer repaymentTermMonths;
    private BigDecimal annualInterestRate;
    private BigDecimal monthlyInstallment;
    private BigDecimal remainingBalance;
    private LoanStatus status;
    private LocalDateTime createdAt;
}
