package com.zafe.bank_system.mapper;

import com.zafe.bank_system.dto.request.CreateCorporateCustomerRequest;
import com.zafe.bank_system.dto.request.CreateIndividualCustomerRequest;
import com.zafe.bank_system.dto.response.CorporateCustomerResponse;
import com.zafe.bank_system.dto.response.IndividualCustomerResponse;
import com.zafe.bank_system.entity.CorporateCustomer;
import com.zafe.bank_system.entity.IndividualCustomer;
import com.zafe.bank_system.enums.CustomerType;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public IndividualCustomer toEntity(CreateIndividualCustomerRequest request) {
        IndividualCustomer customer = new IndividualCustomer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEgn(request.getEgn());
        return customer;
    }

    public CorporateCustomer toEntity(CreateCorporateCustomerRequest request) {
        CorporateCustomer customer = new CorporateCustomer();
        customer.setCompanyName(request.getCompanyName());
        customer.setEik(request.getEik());
        customer.setRepresentativeFirstName(request.getRepresentativeFirstName());
        customer.setRepresentativeLastName(request.getRepresentativeLastName());
        return customer;
    }

    public IndividualCustomerResponse toResponse(IndividualCustomer customer) {
        return IndividualCustomerResponse.builder()
                .id(customer.getId())
                .customerType(CustomerType.INDIVIDUAL)
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .egn(customer.getEgn())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    public CorporateCustomerResponse toResponse(CorporateCustomer customer) {
        return CorporateCustomerResponse.builder()
                .id(customer.getId())
                .customerType(CustomerType.CORPORATE)
                .companyName(customer.getCompanyName())
                .eik(customer.getEik())
                .representativeFirstName(customer.getRepresentativeFirstName())
                .representativeLastName(customer.getRepresentativeLastName())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
