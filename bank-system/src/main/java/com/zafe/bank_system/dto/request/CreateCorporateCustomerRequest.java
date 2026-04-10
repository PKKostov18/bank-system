package com.zafe.bank_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCorporateCustomerRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "EIK is required")
    @Pattern(regexp = "\\d{9}|\\d{13}", message = "EIK must be 9 or 13 digits")
    private String eik;

    @NotBlank(message = "Representative first name is required")
    private String representativeFirstName;

    @NotBlank(message = "Representative last name is required")
    private String representativeLastName;
}
