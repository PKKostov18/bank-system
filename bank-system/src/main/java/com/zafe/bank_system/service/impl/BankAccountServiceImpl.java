package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.dto.request.OpenBankAccountRequest;
import com.zafe.bank_system.dto.response.BankAccountResponse;
import com.zafe.bank_system.entity.BankAccount;
import com.zafe.bank_system.entity.Customer;
import com.zafe.bank_system.enums.AccountStatus;
import com.zafe.bank_system.exception.BankAccountClosedException;
import com.zafe.bank_system.exception.BankAccountNotFoundException;
import com.zafe.bank_system.exception.CustomerNotFoundException;
import com.zafe.bank_system.exception.DuplicateCustomerException;
import com.zafe.bank_system.mapper.BankAccountMapper;
import com.zafe.bank_system.repository.BankAccountRepository;
import com.zafe.bank_system.repository.CustomerRepository;
import com.zafe.bank_system.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    @Transactional
    public BankAccountResponse openAccount(OpenBankAccountRequest request) {
        if (bankAccountRepository.existsByIban(request.getIban())) {
            throw new DuplicateCustomerException("IBAN", request.getIban());
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

        BankAccount account = new BankAccount();
        account.setIban(request.getIban());
        account.setBalance(request.getInitialBalance());
        account.setStatus(AccountStatus.ACTIVE);
        account.setCustomer(customer);

        BankAccount saved = bankAccountRepository.save(account);
        return bankAccountMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public BankAccountResponse closeAccount(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new BankAccountClosedException(accountId);
        }

        account.setStatus(AccountStatus.CLOSED);
        BankAccount saved = bankAccountRepository.save(account);
        return bankAccountMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccountResponse getAccount(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        return bankAccountMapper.toResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountResponse> getAccountsByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        return bankAccountRepository.findAllByCustomerId(customerId)
                .stream()
                .map(bankAccountMapper::toResponse)
                .toList();
    }
}
