package com.nbu.bank_system.domain.model.customer;

import com.nbu.bank_system.domain.model.BaseEntity;
import com.nbu.bank_system.domain.model.account.BankAccount;
import com.nbu.bank_system.domain.model.loan.Loan;
import com.nbu.bank_system.domain.enums.CustomerType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 Абстрактно root customer entity, което дефинира общите полета и поведение за всички типове клиенти
 Играе ролята на owner-side reference към свързаните bank accounts и loans
 */

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "customer_discriminator", discriminatorType = DiscriminatorType.STRING)
public abstract class Customer extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false, length = 20)
    private CustomerType customerType;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<BankAccount> bankAccounts = new HashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Loan> loans = new HashSet<>();

    protected Customer() {
    }

    protected Customer(CustomerType customerType) {
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    protected void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public Set<Loan> getLoans() {
        return loans;
    }
}
