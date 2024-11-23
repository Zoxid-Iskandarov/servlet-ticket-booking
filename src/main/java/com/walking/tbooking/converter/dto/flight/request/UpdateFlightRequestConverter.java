package com.walking.tbooking.converter.dto.flight.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.flight.Flight;
import com.walking.tbooking.model.flight.request.UpdateFlightRequest;

public class UpdateFlightRequestConverter implements Converter<UpdateFlightRequest, Flight> {
    @Override
    public Flight convert(UpdateFlightRequest from) {
        var flight = new Flight();

        flight.setId(from.getId());
        flight.setDepartureDate(from.getDepartureDate());
        flight.setArrivalDate(from.getArrivalDate());
        flight.setDepartureAirportId(from.getDepartureAirportId());
        flight.setArrivalAirportId(from.getArrivalAirportId());
        flight.setTotalSeats(from.getTotalSeats());
        flight.setAvailableSeats(from.getAvailableSeats());

        return flight;
    }
}
