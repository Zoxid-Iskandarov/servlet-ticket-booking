CREATE TABLE flight
(
    id                   BIGSERIAL PRIMARY KEY,
    departure_date       TIMESTAMP NOT NULL,
    arrival_date         TIMESTAMP NOT NULL,
    departure_airport_id INT REFERENCES airport (id),
    arrival_airport_id   INT REFERENCES airport (id),
    total_seats          INT       NOT NULL,
    available_seats      INT       NOT NULL
);