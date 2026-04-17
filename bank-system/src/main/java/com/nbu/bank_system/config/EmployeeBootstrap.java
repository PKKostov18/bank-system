package com.nbu.bank_system.config;

import com.nbu.bank_system.domain.enums.UserRole;
import com.nbu.bank_system.domain.model.customer.IndividualCustomer;
import com.nbu.bank_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EmployeeBootstrap implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.employee.email:employee@bank.local}")
    private String employeeEmail;

    @Value("${app.bootstrap.employee.password:Employee@123}")
    private String employeePassword;

    public EmployeeBootstrap(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String normalizedEmail = employeeEmail.trim().toLowerCase();
        if (customerRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            return;
        }

        IndividualCustomer employee = new IndividualCustomer("System", "Employee", "0000000000");
        employee.assignOnlineBankingCredentials(
                normalizedEmail,
                passwordEncoder.encode(employeePassword),
                false,
                UserRole.EMPLOYEE
        );

        customerRepository.save(employee);
    }
}

