package com.zafe.bank_system.controller;

import com.zafe.bank_system.dto.request.OpenBankAccountRequest;
import com.zafe.bank_system.dto.response.BankAccountResponse;
import com.zafe.bank_system.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccountResponse> openAccount(
            @Valid @RequestBody OpenBankAccountRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankAccountService.openAccount(request));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<BankAccountResponse> closeAccount(@PathVariable Long id) {
        return ResponseEntity.ok(bankAccountService.closeAccount(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(bankAccountService.getAccount(id));
    }

    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getAccountsByCustomer(
            @RequestParam Long customerId
    ) {
        return ResponseEntity.ok(bankAccountService.getAccountsByCustomer(customerId));
    }
}
