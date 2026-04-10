package com.zafe.bank_system.controller;

import com.zafe.bank_system.dto.request.GrantLoanRequest;
import com.zafe.bank_system.dto.response.InstallmentResponse;
import com.zafe.bank_system.dto.response.LoanResponse;
import com.zafe.bank_system.dto.response.LoanStatusResponse;
import com.zafe.bank_system.service.InstallmentService;
import com.zafe.bank_system.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final InstallmentService installmentService;

    @PostMapping
    public ResponseEntity<LoanResponse> grantLoan(
            @Valid @RequestBody GrantLoanRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.grantLoan(request));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<LoanStatusResponse> getLoanStatus(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanStatus(id));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getLoansByCustomer(@RequestParam Long customerId) {
        return ResponseEntity.ok(loanService.getLoansByCustomer(customerId));
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<InstallmentResponse>> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(installmentService.getScheduleByLoan(id));
    }

    @PatchMapping("/installments/{installmentId}/pay")
    public ResponseEntity<InstallmentResponse> markInstallmentAsPaid(
            @PathVariable Long installmentId
    ) {
        return ResponseEntity.ok(installmentService.markAsPaid(installmentId));
    }
}
