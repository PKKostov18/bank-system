package com.nbu.bank_system.service;

public interface OnboardingEmailService {

    void sendTemporaryPasswordEmail(String recipientEmail, String customerDisplayName, String temporaryPassword);
}

