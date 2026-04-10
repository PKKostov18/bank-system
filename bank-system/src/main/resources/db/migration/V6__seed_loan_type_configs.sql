-- V6: Seed default loan type configurations.
-- CONSUMER loan: short-term, higher rate, lower cap.
-- MORTGAGE loan: long-term, lower rate, higher cap.
-- These values can be updated directly in the database without redeployment.

INSERT INTO loan_type_configs (loan_type, annual_interest_rate, max_amount, max_term_months)
VALUES ('CONSUMER', 8.50, 50000.00, 60),
       ('MORTGAGE', 4.50, 1000000.00, 360);
