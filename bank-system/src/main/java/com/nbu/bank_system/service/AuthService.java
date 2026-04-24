package com.nbu.bank_system.service;

import com.nbu.bank_system.dto.auth.AuthResponse;
import com.nbu.bank_system.dto.auth.ChangePasswordRequest;
import com.nbu.bank_system.dto.auth.LoginRequest;
import com.nbu.bank_system.domain.model.customer.Customer;
import com.nbu.bank_system.repository.CustomerRepository;
import com.nbu.bank_system.security.BankUserPrincipal;
import com.nbu.bank_system.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        Customer customer = customerRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), customer.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        BankUserPrincipal principal = BankUserPrincipal.from(customer);
        String token = jwtService.generateToken(principal);

        return new AuthResponse(
                token,
                customer.getId(),
                customer.getEmail(),
                customer.getUserRole(),
                customer.getCustomerType(),
                customer.isFirstLogin()
        );
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        Customer customer = customerRepository.findByEmailIgnoreCase(email.trim().toLowerCase())
                .orElseThrow(() -> new BadCredentialsException("Authenticated user not found"));

        if (!passwordEncoder.matches(request.currentPassword(), customer.getPasswordHash())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        customer.changePassword(passwordEncoder.encode(request.newPassword()));
        customerRepository.save(customer);
    }
}

