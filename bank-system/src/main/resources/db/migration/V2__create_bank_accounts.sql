-- V2: Create the bank_accounts table.
-- Each account is owned by exactly one customer; one customer can have many accounts.

CREATE TABLE bank_accounts
(
    id          BIGINT         NOT NULL AUTO_INCREMENT,
    iban        VARCHAR(34)    NOT NULL,
    balance     DECIMAL(19, 2) NOT NULL,
    status      VARCHAR(10)    NOT NULL,
    customer_id BIGINT         NOT NULL,
    created_at  DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT uq_bank_account_iban UNIQUE (iban),
    CONSTRAINT fk_bank_accounts_customer FOREIGN KEY (customer_id) REFERENCES customers (id)
);
