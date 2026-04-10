-- V1: Create the customers hierarchy tables using JPA JOINED inheritance strategy.
-- The `customers` table holds shared columns; subtype tables hold type-specific columns.

CREATE TABLE customers
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    customer_type VARCHAR(20)  NOT NULL,
    created_at    DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id)
);

CREATE TABLE individual_customers
(
    id         BIGINT       NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    egn        VARCHAR(10)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_individual_egn UNIQUE (egn),
    CONSTRAINT fk_individual_customers FOREIGN KEY (id) REFERENCES customers (id)
);

CREATE TABLE corporate_customers
(
    id                        BIGINT       NOT NULL,
    company_name              VARCHAR(200) NOT NULL,
    eik                       VARCHAR(13)  NOT NULL,
    representative_first_name VARCHAR(100) NOT NULL,
    representative_last_name  VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_corporate_eik UNIQUE (eik),
    CONSTRAINT fk_corporate_customers FOREIGN KEY (id) REFERENCES customers (id)
);
