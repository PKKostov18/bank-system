package com.zafe.bank_system.dto.request;

import com.zafe.bank_system.enums.LoanType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrantLoanRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Loan type is required")
    private LoanType loanType;

    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1.00", message = "Principal amount must be at least 1.00")
    private BigDecimal principalAmount;

    @NotNull(message = "Repayment term is required")
    @Min(value = 1, message = "Repayment term must be at least 1 month")
    private Integer repaymentTermMonths;
}
