package com.walking.tbooking.converter.dto.airport.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.airport.Airport;
import com.walking.tbooking.model.airport.request.UpdateAirportRequest;

public class UpdateAirportRequestConverter implements Converter<UpdateAirportRequest, Airport> {
    @Override
    public Airport convert(UpdateAirportRequest from) {
        var airport = new Airport();

        airport.setId(from.getId());
        airport.setCode(from.getCode());
        airport.setName(from.getName());
        airport.setAddress(from.getAddress());

        return airport;
    }
}
