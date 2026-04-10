package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.entity.Installment;
import com.zafe.bank_system.enums.InstallmentStatus;
import com.zafe.bank_system.service.AnnuityCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard annuity calculation implementation.
 *
 * Annuity formula:
 *   M = P * [r * (1+r)^n] / [(1+r)^n - 1]
 * where:
 *   M = monthly payment
 *   P = principal
 *   r = monthly interest rate  = annualRate / 12 / 100
 *   n = number of monthly payments
 *
 * If r == 0 (zero-interest loan), the formula degenerates to M = P / n.
 *
 * Each period, the interest portion is: remaining_balance * r
 * The principal portion is:            M - interest
 * Remaining balance reduces by the principal portion each month.
 * On the final installment any rounding residual is absorbed to ensure
 * the remaining balance reaches exactly zero.
 */
@Service
public class AnnuityCalculationServiceImpl implements AnnuityCalculationService {

    private static final int MONETARY_SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private static final MathContext HIGH_PRECISION = new MathContext(20, ROUNDING_MODE);

    @Override
    public BigDecimal calculateMonthlyInstallment(
            BigDecimal principal,
            BigDecimal annualRatePercent,
            int termMonths
    ) {
        BigDecimal monthlyRate = toMonthlyRate(annualRatePercent);

        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(termMonths), MONETARY_SCALE, ROUNDING_MODE);
        }

        // (1 + r)^n
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate, HIGH_PRECISION);
        BigDecimal onePlusRPowN = onePlusR.pow(termMonths, HIGH_PRECISION);

        // numerator: P * r * (1+r)^n
        BigDecimal numerator = principal.multiply(monthlyRate, HIGH_PRECISION)
                .multiply(onePlusRPowN, HIGH_PRECISION);

        // denominator: (1+r)^n - 1
        BigDecimal denominator = onePlusRPowN.subtract(BigDecimal.ONE, HIGH_PRECISION);

        return numerator.divide(denominator, MONETARY_SCALE, ROUNDING_MODE);
    }

    @Override
    public List<Installment> generateSchedule(
            BigDecimal principal,
            BigDecimal annualRatePercent,
            int termMonths,
            LocalDate firstDueDate
    ) {
        BigDecimal monthlyRate = toMonthlyRate(annualRatePercent);
        BigDecimal monthlyPayment = calculateMonthlyInstallment(principal, annualRatePercent, termMonths);

        List<Installment> schedule = new ArrayList<>(termMonths);
        BigDecimal remainingBalance = principal;

        for (int i = 1; i <= termMonths; i++) {
            BigDecimal interestPart = remainingBalance
                    .multiply(monthlyRate, HIGH_PRECISION)
                    .setScale(MONETARY_SCALE, ROUNDING_MODE);

            BigDecimal principalPart = monthlyPayment.subtract(interestPart);

            // On the last installment absorb any rounding residual so the balance
            // reaches exactly zero rather than a tiny non-zero amount.
            if (i == termMonths) {
                principalPart = remainingBalance;
                monthlyPayment = principalPart.add(interestPart);
            }

            remainingBalance = remainingBalance.subtract(principalPart)
                    .setScale(MONETARY_SCALE, ROUNDING_MODE);

            Installment installment = new Installment();
            installment.setInstallmentNumber(i);
            installment.setMonthlyAmount(monthlyPayment);
            installment.setPrincipalPart(principalPart);
            installment.setInterestPart(interestPart);
            installment.setRemainingBalance(remainingBalance);
            installment.setDueDate(firstDueDate.plusMonths(i - 1L));
            installment.setStatus(InstallmentStatus.PENDING);

            schedule.add(installment);
        }

        return schedule;
    }

    /**
     * Converts an annual interest rate expressed as a percentage to a monthly decimal rate.
     * e.g. 8.50 (%) -> 0.00708333...
     */
    private BigDecimal toMonthlyRate(BigDecimal annualRatePercent) {
        return annualRatePercent.divide(BigDecimal.valueOf(100), HIGH_PRECISION)
                .divide(BigDecimal.valueOf(12), HIGH_PRECISION);
    }
}
