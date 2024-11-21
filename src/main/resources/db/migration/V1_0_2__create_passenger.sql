CREATE TABLE passenger
(
    id            BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(100)                                 NOT NULL,
    last_name     VARCHAR(100)                                 NOT NULL,
    patronymic    VARCHAR(100)                                 NOT NULL,
    gender        CHAR(6) CHECK (gender IN ('MALE', 'FEMALE')) NOT NULL,
    birth_date    DATE                                         NOT NULL,
    passport_data VARCHAR(50) UNIQUE                           NOT NULL,
    user_id       BIGINT REFERENCES "user" (id) ON DELETE CASCADE
);