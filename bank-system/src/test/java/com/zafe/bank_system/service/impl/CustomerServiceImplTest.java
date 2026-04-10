package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.dto.request.CreateIndividualCustomerRequest;
import com.zafe.bank_system.dto.response.IndividualCustomerResponse;
import com.zafe.bank_system.entity.IndividualCustomer;
import com.zafe.bank_system.exception.DuplicateCustomerException;
import com.zafe.bank_system.mapper.CustomerMapper;
import com.zafe.bank_system.repository.CorporateCustomerRepository;
import com.zafe.bank_system.repository.IndividualCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private IndividualCustomerRepository individualCustomerRepository;

    @Mock
    private CorporateCustomerRepository corporateCustomerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("createIndividualCustomer – success path persists and returns mapped response")
    void createIndividualCustomer_success() {
        CreateIndividualCustomerRequest request = CreateIndividualCustomerRequest.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .egn("9001011234")
                .build();

        IndividualCustomer entity = new IndividualCustomer();
        entity.setFirstName("Ivan");
        entity.setLastName("Petrov");
        entity.setEgn("9001011234");

        IndividualCustomerResponse expectedResponse = IndividualCustomerResponse.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Petrov")
                .egn("9001011234")
                .build();

        when(individualCustomerRepository.existsByEgn("9001011234")).thenReturn(false);
        when(customerMapper.toEntity(request)).thenReturn(entity);
        when(individualCustomerRepository.save(entity)).thenReturn(entity);
        when(customerMapper.toResponse(entity)).thenReturn(expectedResponse);

        IndividualCustomerResponse result = customerService.createIndividualCustomer(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(individualCustomerRepository).save(entity);
    }

    @Test
    @DisplayName("createIndividualCustomer – throws DuplicateCustomerException when EGN already exists")
    void createIndividualCustomer_duplicateEgn_throwsException() {
        CreateIndividualCustomerRequest request = CreateIndividualCustomerRequest.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .egn("9001011234")
                .build();

        when(individualCustomerRepository.existsByEgn("9001011234")).thenReturn(true);

        assertThatThrownBy(() -> customerService.createIndividualCustomer(request))
                .isInstanceOf(DuplicateCustomerException.class)
                .hasMessageContaining("9001011234");

        verify(individualCustomerRepository, never()).save(any());
    }
}
