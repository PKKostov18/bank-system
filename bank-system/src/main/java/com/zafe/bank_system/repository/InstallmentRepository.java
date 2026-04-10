package com.zafe.bank_system.repository;

import com.zafe.bank_system.entity.Installment;
import com.zafe.bank_system.enums.InstallmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {

    List<Installment> findAllByLoanIdOrderByInstallmentNumberAsc(Long loanId);

    Optional<Installment> findByLoanIdAndInstallmentNumber(Long loanId, Integer installmentNumber);

    boolean existsByLoanIdAndStatus(Long loanId, InstallmentStatus status);
}
