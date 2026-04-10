package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.dto.response.InstallmentResponse;
import com.zafe.bank_system.entity.Installment;
import com.zafe.bank_system.entity.Loan;
import com.zafe.bank_system.enums.InstallmentStatus;
import com.zafe.bank_system.enums.LoanStatus;
import com.zafe.bank_system.exception.InstallmentAlreadyPaidException;
import com.zafe.bank_system.exception.InstallmentNotFoundException;
import com.zafe.bank_system.mapper.InstallmentMapper;
import com.zafe.bank_system.repository.InstallmentRepository;
import com.zafe.bank_system.repository.LoanRepository;
import com.zafe.bank_system.service.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final LoanRepository loanRepository;
    private final InstallmentMapper installmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InstallmentResponse> getScheduleByLoan(Long loanId) {
        return installmentRepository.findAllByLoanIdOrderByInstallmentNumberAsc(loanId)
                .stream()
                .map(installmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InstallmentResponse markAsPaid(Long installmentId) {
        Installment installment = installmentRepository.findById(installmentId)
                .orElseThrow(() -> new InstallmentNotFoundException(installmentId));

        if (installment.getStatus() == InstallmentStatus.PAID) {
            throw new InstallmentAlreadyPaidException(installmentId);
        }

        installment.setStatus(InstallmentStatus.PAID);
        installment.setPaidAt(LocalDateTime.now());

        Installment saved = installmentRepository.save(installment);

        updateLoanRemainingBalance(saved.getLoan(), saved);

        return installmentMapper.toResponse(saved);
    }

    /**
     * After marking an installment as paid, deducts the principal portion from the
     * loan's remaining balance. Marks the loan as FULLY_PAID when no pending
     * installments remain.
     */
    private void updateLoanRemainingBalance(Loan loan, Installment paidInstallment) {
        loan.setRemainingBalance(paidInstallment.getRemainingBalance());

        boolean hasAnyPending = installmentRepository.existsByLoanIdAndStatus(
                loan.getId(), InstallmentStatus.PENDING
        );

        if (!hasAnyPending) {
            loan.setStatus(LoanStatus.FULLY_PAID);
        }

        loanRepository.save(loan);
    }
}
