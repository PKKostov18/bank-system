package com.nbu.bank_system.domain.model.account;

import com.nbu.bank_system.domain.model.BaseEntity;
import com.nbu.bank_system.domain.model.customer.Customer;
import com.nbu.bank_system.domain.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
   Представя банкова сметка, притежавана от customer
   Съдържа уникален IBAN, текущ баланс и operational account status
 */

@Entity
@Table(
        name = "bank_accounts",
        uniqueConstraints = {
                @UniqueConstraint(name = "bg_bank_account_iban", columnNames = "iban")
        }
)
public class BankAccount extends BaseEntity {

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}[0-9A-Z]{13,32}$", message = "Invalid IBAN format")
    @Column(name = "iban", nullable = false, length = 34)
    private String iban;

    @NotNull
    @Digits(integer = 19, fraction = 2)
    @DecimalMin(value = "0.00", inclusive = true)
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "owner_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_bank_account_owner")
    )
    private Customer owner;

    protected BankAccount() {
    }

    public BankAccount(String iban, BigDecimal balance, AccountStatus status, Customer owner) {
        this.iban = iban;
        this.balance = balance;
        this.status = status;
        this.owner = owner;
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}

