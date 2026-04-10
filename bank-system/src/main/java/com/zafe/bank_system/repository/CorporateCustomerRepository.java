package com.zafe.bank_system.repository;

import com.zafe.bank_system.entity.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, Long> {

    boolean existsByEik(String eik);

    Optional<CorporateCustomer> findByEik(String eik);
}
