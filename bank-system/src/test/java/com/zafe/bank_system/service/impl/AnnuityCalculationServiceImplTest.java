package com.zafe.bank_system.service.impl;

import com.zafe.bank_system.entity.Installment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the annuity installment calculation logic.
 * No Spring context is required – pure arithmetic verification.
 */
class AnnuityCalculationServiceImplTest {

    private AnnuityCalculationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AnnuityCalculationServiceImpl();
    }

    @Test
    @DisplayName("Monthly installment matches known annuity value for a 12-month loan at 8.5% p.a.")
    void calculateMonthlyInstallment_knownValues() {
        BigDecimal principal = new BigDecimal("10000.00");
        BigDecimal annualRate = new BigDecimal("8.50");
        int termMonths = 12;

        BigDecimal result = service.calculateMonthlyInstallment(principal, annualRate, termMonths);

        // Expected value computed independently: principal=10000, rate=8.5%/yr -> monthly rate≈0.70833%
        // M = 10000 * 0.0070833... * (1.0070833...)^12 / ((1.0070833...)^12 - 1) ≈ 872.20
        assertThat(result).isEqualByComparingTo(new BigDecimal("872.20"));
    }

    @Test
    @DisplayName("Zero-interest loan: each installment equals principal / n")
    void calculateMonthlyInstallment_zeroInterest() {
        BigDecimal principal = new BigDecimal("12000.00");
        BigDecimal annualRate = BigDecimal.ZERO;
        int termMonths = 12;

        BigDecimal result = service.calculateMonthlyInstallment(principal, annualRate, termMonths);

        assertThat(result).isEqualByComparingTo(new BigDecimal("1000.00"));
    }

    @Test
    @DisplayName("Schedule has correct number of installments")
    void generateSchedule_correctSize() {
        BigDecimal principal = new BigDecimal("10000.00");
        BigDecimal annualRate = new BigDecimal("8.50");
        int termMonths = 12;

        List<Installment> schedule = service.generateSchedule(
                principal, annualRate, termMonths, LocalDate.now().plusMonths(1));

        assertThat(schedule).hasSize(termMonths);
    }

    @Test
    @DisplayName("Installment numbers are sequential starting at 1")
    void generateSchedule_installmentNumbersAreSequential() {
        List<Installment> schedule = service.generateSchedule(
                new BigDecimal("10000.00"), new BigDecimal("8.50"), 6,
                LocalDate.of(2025, 1, 1));

        for (int i = 0; i < schedule.size(); i++) {
            assertThat(schedule.get(i).getInstallmentNumber()).isEqualTo(i + 1);
        }
    }

    @Test
    @DisplayName("All installments before the last have the same monthly amount")
    void generateSchedule_fixedMonthlyPaymentBeforeLastInstallment() {
        List<Installment> schedule = service.generateSchedule(
                new BigDecimal("10000.00"), new BigDecimal("8.50"), 12,
                LocalDate.of(2025, 1, 1));

        BigDecimal firstMonthly = schedule.get(0).getMonthlyAmount();
        // Every installment except the last should have the same monthly amount
        for (int i = 0; i < schedule.size() - 1; i++) {
            assertThat(schedule.get(i).getMonthlyAmount())
                    .isEqualByComparingTo(firstMonthly);
        }
    }

    @Test
    @DisplayName("Remaining balance after the last installment is zero")
    void generateSchedule_lastInstallmentLeavesZeroBalance() {
        List<Installment> schedule = service.generateSchedule(
                new BigDecimal("10000.00"), new BigDecimal("8.50"), 12,
                LocalDate.of(2025, 1, 1));

        Installment last = schedule.get(schedule.size() - 1);
        assertThat(last.getRemainingBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Interest part of first installment equals principal * monthly rate")
    void generateSchedule_interestPartIsCorrect() {
        BigDecimal principal = new BigDecimal("10000.00");
        BigDecimal annualRate = new BigDecimal("8.50");
        BigDecimal expectedMonthlyRate = new BigDecimal("8.50")
                .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                .divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);

        List<Installment> schedule = service.generateSchedule(
                principal, annualRate, 12, LocalDate.of(2025, 1, 1));

        BigDecimal expectedFirstInterest = principal.multiply(expectedMonthlyRate)
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(schedule.get(0).getInterestPart())
                .isEqualByComparingTo(expectedFirstInterest);
    }

    @Test
    @DisplayName("Principal part + interest part equals monthly amount for all installments")
    void generateSchedule_partsAddUpToMonthlyAmount() {
        List<Installment> schedule = service.generateSchedule(
                new BigDecimal("10000.00"), new BigDecimal("8.50"), 12,
                LocalDate.of(2025, 1, 1));

        for (Installment installment : schedule) {
            BigDecimal expected = installment.getPrincipalPart().add(installment.getInterestPart());
            assertThat(installment.getMonthlyAmount()).isEqualByComparingTo(expected);
        }
    }

    @Test
    @DisplayName("Interest part decreases monotonically across the schedule")
    void generateSchedule_interestDecreases() {
        List<Installment> schedule = service.generateSchedule(
                new BigDecimal("10000.00"), new BigDecimal("8.50"), 12,
                LocalDate.of(2025, 1, 1));

        for (int i = 1; i < schedule.size() - 1; i++) {
            assertThat(schedule.get(i).getInterestPart())
                    .isLessThan(schedule.get(i - 1).getInterestPart());
        }
    }

    @Test
    @DisplayName("Due dates are one month apart starting from firstDueDate")
    void generateSchedule_dueDatesAreMonthly() {
        LocalDate firstDueDate = LocalDate.of(2025, 2, 1);
        List<Installment> schedule = service.generateSchedule(
                new BigDecimal("10000.00"), new BigDecimal("8.50"), 6, firstDueDate);

        for (int i = 0; i < schedule.size(); i++) {
            assertThat(schedule.get(i).getDueDate()).isEqualTo(firstDueDate.plusMonths(i));
        }
    }
}
