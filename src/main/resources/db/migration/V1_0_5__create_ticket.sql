CREATE TABLE ticket
(
    id                     BIGSERIAL PRIMARY KEY,
    flight_id              BIGINT REFERENCES flight (id) ON DELETE CASCADE,
    passenger_id           BIGINT REFERENCES passenger (id) ON DELETE CASCADE,
    seat_number            CHAR(4)         NOT NULL,
    service_class          VARCHAR(10) CHECK (service_class IN ('ECONOMY', 'BUSINESS', 'FIRST')) NOT NULL,
    baggage_allowance      INT,
    hand_baggage_allowance INT,
    UNIQUE (flight_id, seat_number)
);