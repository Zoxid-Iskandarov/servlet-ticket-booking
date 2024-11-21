CREATE TABLE "user"
(
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    first_name    VARCHAR(100)        NOT NULL,
    last_name     VARCHAR(100)        NOT NULL,
    patronymic    VARCHAR(100)        NOT NULL,
    role          CHAR(5) CHECK (role IN ('USER', 'ADMIN')) DEFAULT 'USER',
    last_login    TIMESTAMP                                     DEFAULT now(),
    is_blocked    BOOLEAN                                       DEFAULT FALSE
);