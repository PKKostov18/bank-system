package com.zafe.bank_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an individual (natural person) customer.
 * Identified uniquely by their Bulgarian personal identification number (EGN).
 */
@Entity
@Table(name = "individual_customers")
@DiscriminatorValue("INDIVIDUAL")
@Getter
@Setter
@NoArgsConstructor
public class IndividualCustomer extends Customer {

    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "egn", nullable = false, unique = true, length = 10)
    @NotBlank(message = "EGN is required")
    @Pattern(regexp = "\\d{10}", message = "EGN must be exactly 10 digits")
    private String egn;
}
