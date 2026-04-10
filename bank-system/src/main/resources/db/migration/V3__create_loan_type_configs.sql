-- V3: Create the loan_type_configs table.
-- Stores configurable loan parameters (interest rate, limits) per loan type.
-- Keeping this in the database allows rate updates without redeployment.

CREATE TABLE loan_type_configs
(
    id                   BIGINT         NOT NULL AUTO_INCREMENT,
    loan_type            VARCHAR(20)    NOT NULL,
    annual_interest_rate DECIMAL(5, 2)  NOT NULL,
    max_amount           DECIMAL(19, 2) NOT NULL,
    max_term_months      INT            NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_loan_type_config_type UNIQUE (loan_type)
);
