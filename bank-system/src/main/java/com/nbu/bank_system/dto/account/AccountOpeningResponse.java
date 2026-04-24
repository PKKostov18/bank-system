package com.nbu.bank_system.dto.account;

import com.nbu.bank_system.domain.enums.AccountStatus;
import java.math.BigDecimal;

public record AccountOpeningResponse(
        boolean created,
        Long accountId,
        String iban,
        BigDecimal balance,
        AccountStatus status,
        String message
) {
}

