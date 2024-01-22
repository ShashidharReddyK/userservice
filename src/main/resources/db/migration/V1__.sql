CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    is_deleted BIT(1) NOT NULL,
    username   VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    phone      VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);