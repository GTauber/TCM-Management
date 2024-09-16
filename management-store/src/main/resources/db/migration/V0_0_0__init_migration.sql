CREATE TABLE store
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid       VARCHAR(36)  NOT NULL DEFAULT (UUID()),
    version    BIGINT       NOT NULL,
    address    VARCHAR(100) NOT NULL,
    zip_code   VARCHAR(10)  NOT NULL,
    city       VARCHAR(50)  NOT NULL,
    created_at DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
