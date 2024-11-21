package com.walking.tbooking.converter.dto.passenger.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.passenger.Passenger;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.model.passenger.request.UpdatePassengerRequest;

public class UpdatePassengerRequestConverter implements Converter<UpdatePassengerRequest, Passenger> {
    @Override
    public Passenger convert(UpdatePassengerRequest from) {
        var passenger = new Passenger();

        passenger.setId(from.getId());
        passenger.setFirstName(from.getFirstName());
        passenger.setLastName(from.getLastName());
        passenger.setPatronymic(from.getPatronymic());
        passenger.setGender(from.getGender());
        passenger.setBirthDate(from.getBirthDate());
        passenger.setPassportData(from.getPassportData());
        passenger.setGender(Role.ADMIN);

        return passenger;
    }
}
