package com.zafe.bank_system.service;

import com.zafe.bank_system.entity.Installment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Computes annuity-based repayment schedules.
 * Separated from loan business logic to follow Single Responsibility Principle
 * and to make the calculation independently testable.
 */
public interface AnnuityCalculationService {

    /**
     * Calculates the fixed monthly installment amount using the standard annuity formula:
     *   M = P * [r(1+r)^n] / [(1+r)^n - 1]
     *
     * @param principal        loan principal amount
     * @param annualRatePercent annual interest rate as a percentage (e.g. 8.50 for 8.50%)
     * @param termMonths       total repayment term in months
     * @return fixed monthly installment amount, rounded to 2 decimal places
     */
    BigDecimal calculateMonthlyInstallment(BigDecimal principal, BigDecimal annualRatePercent, int termMonths);

    /**
     * Generates the full annuity installment schedule for a loan.
     * The schedule breaks each monthly payment into interest and principal components.
     * Interest decreases and principal increases with each successive payment.
     *
     * @param principal        loan principal amount
     * @param annualRatePercent annual interest rate as a percentage
     * @param termMonths       total repayment term in months
     * @param firstDueDate     due date of the first installment
     * @return ordered list of unpersisted Installment entities ready to be linked to a Loan
     */
    List<Installment> generateSchedule(
            BigDecimal principal,
            BigDecimal annualRatePercent,
            int termMonths,
            LocalDate firstDueDate
    );
}
