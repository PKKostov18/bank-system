package com.zafe.bank_system.mapper;

import com.zafe.bank_system.dto.response.InstallmentResponse;
import com.zafe.bank_system.entity.Installment;
import org.springframework.stereotype.Component;

@Component
public class InstallmentMapper {

    public InstallmentResponse toResponse(Installment installment) {
        return InstallmentResponse.builder()
                .id(installment.getId())
                .loanId(installment.getLoan().getId())
                .installmentNumber(installment.getInstallmentNumber())
                .monthlyAmount(installment.getMonthlyAmount())
                .principalPart(installment.getPrincipalPart())
                .interestPart(installment.getInterestPart())
                .remainingBalance(installment.getRemainingBalance())
                .dueDate(installment.getDueDate())
                .status(installment.getStatus())
                .paidAt(installment.getPaidAt())
                .build();
    }
}
