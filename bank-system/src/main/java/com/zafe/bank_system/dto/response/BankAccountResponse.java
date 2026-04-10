package com.zafe.bank_system.dto.response;

import com.zafe.bank_system.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponse {

    private Long id;
    private String iban;
    private BigDecimal balance;
    private AccountStatus status;
    private Long customerId;
    private LocalDateTime createdAt;
}
