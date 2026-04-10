package com.zafe.bank_system.service;

import com.zafe.bank_system.dto.response.InstallmentResponse;

import java.util.List;

public interface InstallmentService {

    List<InstallmentResponse> getScheduleByLoan(Long loanId);

    InstallmentResponse markAsPaid(Long installmentId);
}
