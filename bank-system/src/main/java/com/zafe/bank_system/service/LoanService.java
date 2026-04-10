package com.zafe.bank_system.service;

import com.zafe.bank_system.dto.request.GrantLoanRequest;
import com.zafe.bank_system.dto.response.LoanResponse;
import com.zafe.bank_system.dto.response.LoanStatusResponse;

import java.util.List;

public interface LoanService {

    LoanResponse grantLoan(GrantLoanRequest request);

    LoanStatusResponse getLoanStatus(Long loanId);

    List<LoanResponse> getLoansByCustomer(Long customerId);
}
