package com.walking.tbooking.converter.dto.airport;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.airport.Airport;
import com.walking.tbooking.model.airport.AirportDto;

public class AirportDtoConverter implements Converter<Airport, AirportDto> {
    @Override
    public AirportDto convert(Airport from) {
        var airportDto = new AirportDto();

        airportDto.setCode(from.getCode());
        airportDto.setName(from.getName());
        airportDto.setAddress(from.getAddress());

        return airportDto;
    }
}
