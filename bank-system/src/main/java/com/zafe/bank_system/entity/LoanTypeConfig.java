package com.zafe.bank_system.entity;

import com.zafe.bank_system.enums.LoanType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Stores the configurable parameters for each loan type.
 * Keeping loan-type configuration in the database (rather than hardcoded enums)
 * allows administrators to adjust rates and limits without redeployment.
 *
 * Pre-populated by the Flyway migration V6 with defaults for CONSUMER and MORTGAGE.
 */
@Entity
@Table(name = "loan_type_configs")
@Getter
@Setter
@NoArgsConstructor
public class LoanTypeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false, unique = true, length = 20)
    @NotNull(message = "Loan type is required")
    private LoanType loanType;

    /**
     * Annual interest rate expressed as a percentage, e.g. 8.50 means 8.50% per year.
     */
    @Column(name = "annual_interest_rate", nullable = false, precision = 5, scale = 2)
    @NotNull(message = "Annual interest rate is required")
    @DecimalMin(value = "0.01", message = "Interest rate must be positive")
    @DecimalMax(value = "100.00", message = "Interest rate cannot exceed 100%")
    private BigDecimal annualInterestRate;

    @Column(name = "max_amount", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Maximum loan amount is required")
    @DecimalMin(value = "1.00", message = "Maximum amount must be positive")
    private BigDecimal maxAmount;

    @Column(name = "max_term_months", nullable = false)
    @NotNull(message = "Maximum repayment term is required")
    @Min(value = 1, message = "Repayment term must be at least 1 month")
    @Max(value = 600, message = "Repayment term cannot exceed 600 months (50 years)")
    private Integer maxTermMonths;
}
