-- V5: Create the installments table.
-- Each row represents one monthly payment entry in an annuity-based repayment schedule.

CREATE TABLE installments
(
    id                  BIGINT         NOT NULL AUTO_INCREMENT,
    loan_id             BIGINT         NOT NULL,
    installment_number  INT            NOT NULL,
    monthly_amount      DECIMAL(19, 2) NOT NULL,
    principal_part      DECIMAL(19, 2) NOT NULL,
    interest_part       DECIMAL(19, 2) NOT NULL,
    remaining_balance   DECIMAL(19, 2) NOT NULL,
    due_date            DATE           NOT NULL,
    status              VARCHAR(10)    NOT NULL,
    paid_at             DATETIME(6)    NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_loan_installment_number UNIQUE (loan_id, installment_number),
    CONSTRAINT fk_installments_loan FOREIGN KEY (loan_id) REFERENCES loans (id)
);
