package com.zafe.bank_system.repository;

import com.zafe.bank_system.entity.Loan;
import com.zafe.bank_system.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByCustomerId(Long customerId);

    List<Loan> findAllByCustomerIdAndStatus(Long customerId, LoanStatus status);
}
