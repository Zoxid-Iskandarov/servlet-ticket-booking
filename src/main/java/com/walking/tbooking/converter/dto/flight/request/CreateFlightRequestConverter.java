package com.walking.tbooking.converter.dto.flight.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.flight.Flight;
import com.walking.tbooking.model.flight.request.CreateFlightRequest;

public class CreateFlightRequestConverter implements Converter<CreateFlightRequest, Flight> {
    @Override
    public Flight convert(CreateFlightRequest from) {
        var flight = new Flight();

        flight.setDepartureDate(from.getDepartureDate());
        flight.setArrivalDate(from.getArrivalDate());
        flight.setDepartureAirportId(from.getDepartureAirportId());
        flight.setArrivalAirportId(from.getArrivalAirportId());
        flight.setTotalSeats(from.getTotalSeats());
        flight.setAvailableSeats(from.getAvailableSeats());

        return flight;
    }
}
