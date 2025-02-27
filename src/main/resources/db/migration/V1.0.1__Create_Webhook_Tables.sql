CREATE TABLE IF NOT EXISTS delivery_webhook (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trackingId VARCHAR(255) NOT NULL,
    deliveryStatus VARCHAR(255) NOT NULL,
    deliveryDate TIMESTAMP NOT NULL,
    deliveryReturnReason VARCHAR(255),
    deliveryAddress VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cvv_webhook (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accountId BIGINT NOT NULL,
    cardId BIGINT NOT NULL,
    nextCvv VARCHAR(255) NOT NULL,
    expirationDate DATE NOT NULL,
    CONSTRAINT fk_cvv_webhook_account FOREIGN KEY (accountId) REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT fk_cvv_webhook_card FOREIGN KEY (cardId) REFERENCES cards(id) ON DELETE CASCADE
);

