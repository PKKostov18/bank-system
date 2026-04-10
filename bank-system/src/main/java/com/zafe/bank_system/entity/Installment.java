package com.zafe.bank_system.entity;

import com.zafe.bank_system.enums.InstallmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A single entry in the annuity-based repayment schedule of a loan.
 *
 * Each installment has a fixed total monthly amount split into:
 *   - interest part  : interest charged on the remaining principal this month
 *   - principal part : the portion that reduces the outstanding balance
 *
 * At the start of the loan the interest part is larger; it shrinks every month
 * as the remaining balance decreases, while the principal part grows correspondingly.
 */
@Entity
@Table(
        name = "installments",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_loan_installment_number",
                columnNames = {"loan_id", "installment_number"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    @NotNull(message = "Installment must belong to a loan")
    private Loan loan;

    @Column(name = "installment_number", nullable = false)
    @NotNull(message = "Installment number is required")
    @Min(value = 1, message = "Installment number must be positive")
    private Integer installmentNumber;

    @Column(name = "monthly_amount", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Monthly amount is required")
    private BigDecimal monthlyAmount;

    @Column(name = "principal_part", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Principal part is required")
    private BigDecimal principalPart;

    @Column(name = "interest_part", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Interest part is required")
    private BigDecimal interestPart;

    /**
     * Remaining principal balance after this installment is applied.
     */
    @Column(name = "remaining_balance", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Remaining balance is required")
    private BigDecimal remainingBalance;

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    @NotNull(message = "Installment status is required")
    private InstallmentStatus status;

    /** Set when the installment is marked as paid. */
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
