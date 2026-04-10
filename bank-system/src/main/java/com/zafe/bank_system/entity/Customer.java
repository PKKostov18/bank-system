package com.zafe.bank_system.entity;

import com.zafe.bank_system.enums.CustomerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base entity for all customer types.
 * Uses JPA JOINED inheritance strategy to maintain a clean, normalized schema:
 *   - customers table holds shared identity and audit columns
 *   - individual_customers / corporate_customers tables hold type-specific fields
 * This avoids nullable columns for type-specific data, preserves referential integrity,
 * and makes it easy to add new customer types without altering the base table.
 */
@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false, length = 20, insertable = false, updatable = false)
    private CustomerType customerType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();
}
