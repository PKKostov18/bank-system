package com.zafe.bank_system.entity;

import com.zafe.bank_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a bank account owned by a customer.
 * One customer can hold multiple accounts.
 * Balance is stored as BigDecimal to guarantee decimal precision for monetary values.
 */
@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iban", nullable = false, unique = true, length = 34)
    @NotBlank(message = "IBAN is required")
    @Pattern(
            regexp = "[A-Z]{2}\\d{2}[A-Z0-9]{1,30}",
            message = "IBAN must start with 2 letters followed by 2 digits and up to 30 alphanumeric characters"
    )
    private String iban;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    @NotNull(message = "Account status is required")
    private AccountStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Account must have an owner")
    private Customer customer;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
