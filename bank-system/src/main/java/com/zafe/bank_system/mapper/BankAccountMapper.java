package com.zafe.bank_system.mapper;

import com.zafe.bank_system.dto.response.BankAccountResponse;
import com.zafe.bank_system.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccountResponse toResponse(BankAccount account) {
        return BankAccountResponse.builder()
                .id(account.getId())
                .iban(account.getIban())
                .balance(account.getBalance())
                .status(account.getStatus())
                .customerId(account.getCustomer().getId())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
