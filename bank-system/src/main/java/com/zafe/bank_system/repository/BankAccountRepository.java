package com.zafe.bank_system.repository;

import com.zafe.bank_system.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByIban(String iban);

    Optional<BankAccount> findByIban(String iban);

    List<BankAccount> findAllByCustomerId(Long customerId);
}
