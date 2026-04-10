package com.zafe.bank_system.controller;

import com.zafe.bank_system.dto.request.CreateCorporateCustomerRequest;
import com.zafe.bank_system.dto.request.CreateIndividualCustomerRequest;
import com.zafe.bank_system.dto.response.CorporateCustomerResponse;
import com.zafe.bank_system.dto.response.IndividualCustomerResponse;
import com.zafe.bank_system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/individual")
    public ResponseEntity<IndividualCustomerResponse> createIndividual(
            @Valid @RequestBody CreateIndividualCustomerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createIndividualCustomer(request));
    }

    @PostMapping("/corporate")
    public ResponseEntity<CorporateCustomerResponse> createCorporate(
            @Valid @RequestBody CreateCorporateCustomerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCorporateCustomer(request));
    }
}
