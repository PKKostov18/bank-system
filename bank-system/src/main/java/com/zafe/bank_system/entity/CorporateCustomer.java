package com.zafe.bank_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a corporate (legal entity) customer.
 * Identified uniquely by the Bulgarian company identification number (EIK).
 * Also stores the natural-person representative responsible for the account.
 */
@Entity
@Table(name = "corporate_customers")
@DiscriminatorValue("CORPORATE")
@Getter
@Setter
@NoArgsConstructor
public class CorporateCustomer extends Customer {

    @Column(name = "company_name", nullable = false, length = 200)
    @NotBlank(message = "Company name is required")
    private String companyName;

    @Column(name = "eik", nullable = false, unique = true, length = 13)
    @NotBlank(message = "EIK is required")
    @Pattern(regexp = "\\d{9}|\\d{13}", message = "EIK must be 9 or 13 digits")
    private String eik;

    @Column(name = "representative_first_name", nullable = false, length = 100)
    @NotBlank(message = "Representative first name is required")
    private String representativeFirstName;

    @Column(name = "representative_last_name", nullable = false, length = 100)
    @NotBlank(message = "Representative last name is required")
    private String representativeLastName;
}
