CREATE TABLE airport
(
    id      SERIAL PRIMARY KEY,
    code    VARCHAR(10) UNIQUE NOT NULL,
    name    VARCHAR(100)       NOT NULL,
    address TEXT
);