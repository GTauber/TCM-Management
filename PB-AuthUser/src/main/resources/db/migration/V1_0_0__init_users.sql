CREATE TABLE user
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    uuid         CHAR(36)     NOT NULL UNIQUE,
    name         VARCHAR(50)  NOT NULL,
    lastname     VARCHAR(50)  NOT NULL,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    email        VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    birthday     DATE         NOT NULL,
    full_name    VARCHAR(150) NOT NULL,
    user_status  VARCHAR(15)  NOT NULL,
    user_role    VARCHAR(15)  NOT NULL,
    phone_number VARCHAR(20),
    cpf          VARCHAR(20),
    image_url    VARCHAR(255),
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);