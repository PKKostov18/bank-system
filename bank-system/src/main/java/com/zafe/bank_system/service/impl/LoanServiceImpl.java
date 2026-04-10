package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.dto.request.GrantLoanRequest;
import com.zafe.bank_system.dto.response.LoanResponse;
import com.zafe.bank_system.dto.response.LoanStatusResponse;
import com.zafe.bank_system.entity.Customer;
import com.zafe.bank_system.entity.Installment;
import com.zafe.bank_system.entity.Loan;
import com.zafe.bank_system.entity.LoanTypeConfig;
import com.zafe.bank_system.enums.InstallmentStatus;
import com.zafe.bank_system.enums.LoanStatus;
import com.zafe.bank_system.exception.CustomerNotFoundException;
import com.zafe.bank_system.exception.LoanAmountExceedsLimitException;
import com.zafe.bank_system.exception.LoanNotFoundException;
import com.zafe.bank_system.exception.LoanTermExceedsLimitException;
import com.zafe.bank_system.exception.LoanTypeConfigNotFoundException;
import com.zafe.bank_system.mapper.LoanMapper;
import com.zafe.bank_system.repository.CustomerRepository;
import com.zafe.bank_system.repository.LoanRepository;
import com.zafe.bank_system.repository.LoanTypeConfigRepository;
import com.zafe.bank_system.service.AnnuityCalculationService;
import com.zafe.bank_system.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanTypeConfigRepository loanTypeConfigRepository;
    private final AnnuityCalculationService annuityCalculationService;
    private final LoanMapper loanMapper;

    @Override
    @Transactional
    public LoanResponse grantLoan(GrantLoanRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

        LoanTypeConfig config = loanTypeConfigRepository.findByLoanType(request.getLoanType())
                .orElseThrow(() -> new LoanTypeConfigNotFoundException(request.getLoanType()));

        validateLoanConstraints(request, config);

        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanTypeConfig(config);
        loan.setPrincipalAmount(request.getPrincipalAmount());
        loan.setRepaymentTermMonths(request.getRepaymentTermMonths());
        loan.setRemainingBalance(request.getPrincipalAmount());
        loan.setStatus(LoanStatus.ACTIVE);

        loan.setMonthlyInstallment(annuityCalculationService.calculateMonthlyInstallment(
                request.getPrincipalAmount(),
                config.getAnnualInterestRate(),
                request.getRepaymentTermMonths()
        ));

        List<Installment> schedule = annuityCalculationService.generateSchedule(
                request.getPrincipalAmount(),
                config.getAnnualInterestRate(),
                request.getRepaymentTermMonths(),
                LocalDate.now().plusMonths(1)
        );

        schedule.forEach(installment -> installment.setLoan(loan));
        loan.getInstallments().addAll(schedule);

        Loan saved = loanRepository.save(loan);
        return loanMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanStatusResponse getLoanStatus(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));

        List<Installment> installments = loan.getInstallments();
        long paidCount = installments.stream()
                .filter(i -> i.getStatus() == InstallmentStatus.PAID)
                .count();
        long pendingCount = installments.stream()
                .filter(i -> i.getStatus() == InstallmentStatus.PENDING)
                .count();

        return LoanStatusResponse.builder()
                .loanId(loan.getId())
                .loanType(loan.getLoanTypeConfig().getLoanType())
                .status(loan.getStatus())
                .principalAmount(loan.getPrincipalAmount())
                .remainingBalance(loan.getRemainingBalance())
                .totalInstallments(installments.size())
                .paidInstallments(paidCount)
                .pendingInstallments(pendingCount)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> getLoansByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        return loanRepository.findAllByCustomerId(customerId)
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    private void validateLoanConstraints(GrantLoanRequest request, LoanTypeConfig config) {
        if (request.getPrincipalAmount().compareTo(config.getMaxAmount()) > 0) {
            throw new LoanAmountExceedsLimitException(request.getPrincipalAmount(), config.getMaxAmount());
        }
        if (request.getRepaymentTermMonths() > config.getMaxTermMonths()) {
            throw new LoanTermExceedsLimitException(request.getRepaymentTermMonths(), config.getMaxTermMonths());
        }
    }
}
