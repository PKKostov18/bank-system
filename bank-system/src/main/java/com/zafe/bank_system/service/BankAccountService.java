package com.zafe.bank_system.service;

import com.zafe.bank_system.dto.request.OpenBankAccountRequest;
import com.zafe.bank_system.dto.response.BankAccountResponse;

import java.util.List;

public interface BankAccountService {

    BankAccountResponse openAccount(OpenBankAccountRequest request);

    BankAccountResponse closeAccount(Long accountId);

    BankAccountResponse getAccount(Long accountId);

    List<BankAccountResponse> getAccountsByCustomer(Long customerId);
}
