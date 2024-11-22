package com.walking.tbooking.converter.dto.passenger.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.passenger.Passenger;
import com.walking.tbooking.model.passenger.request.CreatePassengerRequest;

public class CreatePassengerRequestConverter implements Converter<CreatePassengerRequest, Passenger> {
    @Override
    public Passenger convert(CreatePassengerRequest from) {
        var passenger = new Passenger();

        passenger.setFirstName(from.getFirstName());
        passenger.setLastName(from.getLastName());
        passenger.setPatronymic(from.getPatronymic());
        passenger.setGender(from.getGender());
        passenger.setBirthDate(from.getBirthDate());
        passenger.setPassportData(from.getPassportData());
        passenger.setUserId(from.getUserId());

        return passenger;
    }
}
