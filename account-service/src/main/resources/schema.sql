CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    account_type VARCHAR(255),
    balance DECIMAL(19,2),
    status BOOLEAN,
    client_id BIGINT NOT NULL
);

CREATE TABLE movement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(19,2) NOT NULL,
    initial_balance DECIMAL(19,2),
    movement_type VARCHAR(50),
    transaction_date TIMESTAMP,
    account_id BIGINT
);