package com.nbu.bank_system.dto.auth;

import com.nbu.bank_system.domain.enums.UserRole;

public record AuthResponse(
        String token,
        Long customerId,
        String email,
        UserRole role,
        boolean firstLogin
) {
}

