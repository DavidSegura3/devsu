-- file: data.sql

-- Insert data in table accounts
INSERT INTO accounts (account_number, account_type, initial_balance, status, created_user, created_date) VALUES
('478758', 'savings', 2000.00, true, 'devsu', CURRENT_TIMESTAMP()),
('225487', 'checking', 100.00, true, 'devsu', CURRENT_TIMESTAMP()),
('495878', 'savings', 0.00, true, 'devsu', CURRENT_TIMESTAMP()),
('496825', 'savings', 540.00, true, 'devsu', CURRENT_TIMESTAMP());
