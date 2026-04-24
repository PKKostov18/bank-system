package com.nbu.bank_system.repository;

import com.nbu.bank_system.domain.model.account.BankAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByOwnerId(Long ownerId);

    Optional<BankAccount> findFirstByOwnerIdOrderByIdAsc(Long ownerId);

    boolean existsByIban(String iban);
}

