package com.zafe.bank_system.mapper;

import com.zafe.bank_system.dto.response.LoanResponse;
import com.zafe.bank_system.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .customerId(loan.getCustomer().getId())
                .loanType(loan.getLoanTypeConfig().getLoanType())
                .principalAmount(loan.getPrincipalAmount())
                .repaymentTermMonths(loan.getRepaymentTermMonths())
                .annualInterestRate(loan.getLoanTypeConfig().getAnnualInterestRate())
                .monthlyInstallment(loan.getMonthlyInstallment())
                .remainingBalance(loan.getRemainingBalance())
                .status(loan.getStatus())
                .createdAt(loan.getCreatedAt())
                .build();
    }
}
