CREATE TABLE product
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid           CHAR(36)     NOT NULL UNIQUE,
    user_id        BIGINT       NOT NULL,
    version        BIGINT       NOT NULL,
    name           VARCHAR(50)  NOT NULL,
    description    VARCHAR(255) NOT NULL,
    model          VARCHAR(15)  NOT NULL,
    specifications VARCHAR(255),
    image_url      VARCHAR(100) NOT NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE bid
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid        CHAR(36)       NOT NULL UNIQUE,
    version     BIGINT         NOT NULL,
    auction_id  BIGINT         NOT NULL,
    bidder_uuid CHAR(36)         NOT NULL,
    amount      DECIMAL(10, 2) NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE auction
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid               CHAR(36)       NOT NULL UNIQUE,
    version            BIGINT         NOT NULL,
    bid_winner_id      BIGINT,
    product_id         BIGINT         NOT NULL,
    auction_owner_uuid CHAR(36)       NOT NULL,
    auction_start_date DATETIME       NOT NULL,
    auction_end_date   DATETIME       NOT NULL,
    auction_status     VARCHAR(15)    NOT NULL,
    initial_price      DECIMAL(10, 2) NOT NULL,
    created_at         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (bid_winner_id) REFERENCES bid (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

ALTER TABLE bid
    ADD CONSTRAINT fk_bid_auction
        FOREIGN KEY (auction_id) REFERENCES auction (id);



