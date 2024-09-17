CREATE TABLE IF NOT EXISTS `barber`
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    version    BIGINT       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    user_type  VARCHAR(30)  NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_barber PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS `booking`
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    version        BIGINT      NOT NULL,
    user_id        BIGINT      NOT NULL,
    barber_id      BIGINT      NOT NULL,
    start_datetime DATETIME    NOT NULL,
    duration       INT         NOT NULL,
    end_datetime   DATETIME    NOT NULL,
    services       VARCHAR(40) NOT NULL,
    created_at     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_booking PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

