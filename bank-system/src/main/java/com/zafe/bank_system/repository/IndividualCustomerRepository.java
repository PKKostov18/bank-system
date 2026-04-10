package com.zafe.bank_system.repository;

import com.zafe.bank_system.entity.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, Long> {

    boolean existsByEgn(String egn);

    Optional<IndividualCustomer> findByEgn(String egn);
}
