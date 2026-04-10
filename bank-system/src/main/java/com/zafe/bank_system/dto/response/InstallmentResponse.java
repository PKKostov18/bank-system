package com.zafe.bank_system.dto.response;

import com.zafe.bank_system.enums.InstallmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentResponse {

    private Long id;
    private Long loanId;
    private Integer installmentNumber;
    private BigDecimal monthlyAmount;
    private BigDecimal principalPart;
    private BigDecimal interestPart;
    private BigDecimal remainingBalance;
    private LocalDate dueDate;
    private InstallmentStatus status;
    private LocalDateTime paidAt;
}
