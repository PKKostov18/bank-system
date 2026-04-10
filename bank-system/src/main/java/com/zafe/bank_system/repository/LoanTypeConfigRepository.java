package com.zafe.bank_system.repository;

import com.zafe.bank_system.entity.LoanTypeConfig;
import com.zafe.bank_system.enums.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTypeConfigRepository extends JpaRepository<LoanTypeConfig, Long> {

    Optional<LoanTypeConfig> findByLoanType(LoanType loanType);
}
