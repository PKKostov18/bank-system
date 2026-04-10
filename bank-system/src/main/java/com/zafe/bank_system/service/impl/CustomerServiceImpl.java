package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.dto.request.CreateCorporateCustomerRequest;
import com.zafe.bank_system.dto.request.CreateIndividualCustomerRequest;
import com.zafe.bank_system.dto.response.CorporateCustomerResponse;
import com.zafe.bank_system.dto.response.IndividualCustomerResponse;
import com.zafe.bank_system.entity.CorporateCustomer;
import com.zafe.bank_system.entity.IndividualCustomer;
import com.zafe.bank_system.exception.DuplicateCustomerException;
import com.zafe.bank_system.mapper.CustomerMapper;
import com.zafe.bank_system.repository.CorporateCustomerRepository;
import com.zafe.bank_system.repository.IndividualCustomerRepository;
import com.zafe.bank_system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final IndividualCustomerRepository individualCustomerRepository;
    private final CorporateCustomerRepository corporateCustomerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public IndividualCustomerResponse createIndividualCustomer(CreateIndividualCustomerRequest request) {
        if (individualCustomerRepository.existsByEgn(request.getEgn())) {
            throw new DuplicateCustomerException("EGN", request.getEgn());
        }

        IndividualCustomer customer = customerMapper.toEntity(request);
        IndividualCustomer saved = individualCustomerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CorporateCustomerResponse createCorporateCustomer(CreateCorporateCustomerRequest request) {
        if (corporateCustomerRepository.existsByEik(request.getEik())) {
            throw new DuplicateCustomerException("EIK", request.getEik());
        }

        CorporateCustomer customer = customerMapper.toEntity(request);
        CorporateCustomer saved = corporateCustomerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }
}
