package com.walking.tbooking.converter.dto.flight;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.flight.Flight;
import com.walking.tbooking.model.flight.FlightDto;

public class FlightDtoConverter implements Converter<Flight, FlightDto> {
    @Override
    public FlightDto convert(Flight from) {
        var flightDto = new FlightDto();

        flightDto.setDepartureDate(from.getDepartureDate());
        flightDto.setArrivalDate(from.getArrivalDate());
        flightDto.setDepartureAirportId(from.getDepartureAirportId());
        flightDto.setArrivalAirportId(from.getArrivalAirportId());
        flightDto.setTotalSeats(from.getTotalSeats());
        flightDto.setAvailableSeats(from.getAvailableSeats());

        return flightDto;
    }
}
