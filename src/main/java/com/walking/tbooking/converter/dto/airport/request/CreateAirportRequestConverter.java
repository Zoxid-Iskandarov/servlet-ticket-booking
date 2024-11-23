package com.walking.tbooking.converter.dto.airport.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.airport.Airport;
import com.walking.tbooking.model.airport.request.CreateAirportRequest;

public class CreateAirportRequestConverter implements Converter<CreateAirportRequest, Airport> {
    @Override
    public Airport convert(CreateAirportRequest from) {
        var airport = new Airport();

        airport.setCode(from.getCode());
        airport.setName(from.getName());
        airport.setAddress(from.getAddress());

        return airport;
    }
}
