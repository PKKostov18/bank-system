CREATE TABLE IF NOT EXISTS customers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    customer_discriminator VARCHAR(31) NOT NULL,
    customer_type ENUM('INDIVIDUAL', 'CORPORATE') NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    is_first_login BIT NOT NULL,
    user_role ENUM('CUSTOMER', 'EMPLOYEE') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_customers_email (email)
);

CREATE TABLE IF NOT EXISTS individual_customers (
    id BIGINT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    egn VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_individual_customer_egn (egn),
    CONSTRAINT fk_individual_customer_base
        FOREIGN KEY (id) REFERENCES customers (id)
);

CREATE TABLE IF NOT EXISTS corporate_customers (
    id BIGINT NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    eik VARCHAR(13) NOT NULL,
    representative_first_name VARCHAR(100) NOT NULL,
    representative_last_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_corporate_customer_eik (eik),
    CONSTRAINT fk_corporate_customer_base
        FOREIGN KEY (id) REFERENCES customers (id)
);

CREATE TABLE IF NOT EXISTS bank_accounts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    iban VARCHAR(34) NOT NULL,
    balance DECIMAL(19,2) NOT NULL,
    status ENUM('ACTIVE', 'CLOSED') NOT NULL,
    owner_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY bg_bank_account_iban (iban),
    CONSTRAINT fk_bank_account_owner
        FOREIGN KEY (owner_id) REFERENCES customers (id)
);

CREATE TABLE IF NOT EXISTS loans (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    customer_id BIGINT NOT NULL,
    loan_type ENUM('CONSUMER', 'MORTGAGE') NOT NULL,
    principal_amount DECIMAL(19,2) NOT NULL,
    annual_interest_rate DECIMAL(9,4) NOT NULL,
    repayment_term_months INT NOT NULL,
    status ENUM('ACTIVE', 'CLOSED') NOT NULL,
    start_date DATE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_loan_customer
        FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE TABLE IF NOT EXISTS installments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    loan_id BIGINT NOT NULL,
    installment_number INT NOT NULL,
    due_date DATE NOT NULL,
    monthly_installment_amount DECIMAL(19,2) NOT NULL,
    principal_part DECIMAL(19,2) NOT NULL,
    interest_part DECIMAL(19,2) NOT NULL,
    remaining_balance DECIMAL(19,2) NOT NULL,
    status ENUM('PENDING', 'PAID', 'OVERDUE') NOT NULL,
    paid_at DATETIME(6) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_installment_loan_installment_number (loan_id, installment_number),
    CONSTRAINT fk_installment_loan
        FOREIGN KEY (loan_id) REFERENCES loans (id)
);

