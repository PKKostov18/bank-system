-- V4: Create the loans table.
-- Links a customer to a loan type config and records the principal, term,
-- pre-computed monthly installment and outstanding balance.

CREATE TABLE loans
(
    id                    BIGINT         NOT NULL AUTO_INCREMENT,
    customer_id           BIGINT         NOT NULL,
    loan_type_config_id   BIGINT         NOT NULL,
    principal_amount      DECIMAL(19, 2) NOT NULL,
    repayment_term_months INT            NOT NULL,
    monthly_installment   DECIMAL(19, 2) NOT NULL,
    remaining_balance     DECIMAL(19, 2) NOT NULL,
    status                VARCHAR(20)    NOT NULL,
    created_at            DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_loans_customer        FOREIGN KEY (customer_id)         REFERENCES customers (id),
    CONSTRAINT fk_loans_loan_type_config FOREIGN KEY (loan_type_config_id) REFERENCES loan_type_configs (id)
);
