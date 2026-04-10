package com.zafe.bank_system.service;

import com.zafe.bank_system.dto.request.CreateCorporateCustomerRequest;
import com.zafe.bank_system.dto.request.CreateIndividualCustomerRequest;
import com.zafe.bank_system.dto.response.CorporateCustomerResponse;
import com.zafe.bank_system.dto.response.IndividualCustomerResponse;

public interface CustomerService {

    IndividualCustomerResponse createIndividualCustomer(CreateIndividualCustomerRequest request);

    CorporateCustomerResponse createCorporateCustomer(CreateCorporateCustomerRequest request);
}
