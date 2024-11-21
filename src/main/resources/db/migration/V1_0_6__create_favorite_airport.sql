CREATE TABLE favorite_airport
(
    id           BIGSERIAL PRIMARY KEY,
    passenger_id BIGINT REFERENCES passenger (id) ON DELETE CASCADE,
    airport_id   INT REFERENCES airport (id) ON DELETE CASCADE,
    rank         INT CHECK ( rank BETWEEN 1 AND 3),
    UNIQUE (passenger_id, rank)
);