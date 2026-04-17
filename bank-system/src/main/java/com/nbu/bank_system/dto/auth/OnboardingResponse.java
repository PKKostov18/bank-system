package com.nbu.bank_system.dto.auth;

import com.nbu.bank_system.domain.enums.CustomerType;

public record OnboardingResponse(
        Long customerId,
        String email,
        CustomerType customerType,
        boolean temporaryPasswordSent,
        String emailDeliveryChannel,
        String emailRelay
) {
}

