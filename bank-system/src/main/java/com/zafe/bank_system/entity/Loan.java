package com.zafe.bank_system.entity;

import com.zafe.bank_system.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a loan granted to a customer.
 * Links the customer to the loan type configuration, records the principal,
 * repayment term and pre-computed monthly installment amount.
 *
 * The monthly installment is calculated once at grant time using the
 * annuity formula and stored to ensure consistency throughout the loan lifetime.
 */
@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Loan must be associated with a customer")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_config_id", nullable = false)
    @NotNull(message = "Loan type configuration is required")
    private LoanTypeConfig loanTypeConfig;

    @Column(name = "principal_amount", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1.00", message = "Principal amount must be positive")
    private BigDecimal principalAmount;

    @Column(name = "repayment_term_months", nullable = false)
    @NotNull(message = "Repayment term is required")
    @Min(value = 1, message = "Repayment term must be at least 1 month")
    private Integer repaymentTermMonths;

    /**
     * Fixed monthly installment amount computed via the annuity formula at grant time.
     * Remains constant throughout the loan period.
     */
    @Column(name = "monthly_installment", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Monthly installment is required")
    private BigDecimal monthlyInstallment;

    /**
     * Outstanding principal balance that decreases as installments are paid.
     */
    @Column(name = "remaining_balance", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Remaining balance is required")
    private BigDecimal remainingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Loan status is required")
    private LoanStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("installmentNumber ASC")
    private List<Installment> installments = new ArrayList<>();
}
