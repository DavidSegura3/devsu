-- file: schema.sql

-- table accounts
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) UNIQUE NOT NULL,
    account_type VARCHAR(255) NOT NULL,
    initial_balance DECIMAL(19, 2) NOT NULL,
    status BOOLEAN,
    created_user VARCHAR(255),
    created_date TIMESTAMP
);

-- table movements
CREATE TABLE IF NOT EXISTS movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movement_type VARCHAR(255),
    transaction_value DECIMAL(19, 2) NOT NULL,
    balance DECIMAL(19, 2),
    created_user VARCHAR(255),
    created_date TIMESTAMP,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);