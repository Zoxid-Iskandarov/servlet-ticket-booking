package com.walking.tbooking.model.flight.request;

import java.time.LocalDateTime;

public class UpdateFlightRequest {
    private Long id;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Integer departureAirportId;
    private Integer arrivalAirportId;
    private Integer totalSeats;
    private Integer availableSeats;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public Integer getDepartureAirportId() {
        return departureAirportId;
    }

    public Integer getArrivalAirportId() {
        return arrivalAirportId;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }
}
