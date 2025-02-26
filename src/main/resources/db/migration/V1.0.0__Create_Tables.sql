-- Criar tabela addresses
CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(8) NOT NULL,
    country VARCHAR(255) NOT NULL
);

-- Criar tabela customers
CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    document VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address_id BIGINT,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

-- Criar tabela accounts
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    CONSTRAINT fk_account_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Criar tabela cards
CREATE TABLE IF NOT EXISTS cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    type ENUM('CREDIT', 'DEBIT') NOT NULL,
    card_holder VARCHAR(255) NOT NULL,
    number VARCHAR(19) NOT NULL UNIQUE,
    cvv VARCHAR(3) NOT NULL,
    expiration_date DATE NOT NULL,
    deliveryStatus ENUM('DELIVERED', 'PENDING', 'SHIPPED'),
    status ENUM('PENDING', 'ACTIVE', 'BLOCKED', 'CANCELLED', 'EXPIRED') NOT NULL,
    CONSTRAINT fk_card_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- Criar tabela card_reissues
CREATE TABLE IF NOT EXISTS card_reissues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_id BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    request_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_card_reissues_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);