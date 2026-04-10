package com.zafe.bank_system.dto.response;

import com.zafe.bank_system.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerResponse {

    private Long id;
    private CustomerType customerType;
    private String companyName;
    private String eik;
    private String representativeFirstName;
    private String representativeLastName;
    private LocalDateTime createdAt;
}
