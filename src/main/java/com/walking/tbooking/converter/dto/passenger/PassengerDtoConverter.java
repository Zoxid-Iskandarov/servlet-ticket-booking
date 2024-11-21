package com.walking.tbooking.converter.dto.passenger;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.passenger.Passenger;
import com.walking.tbooking.model.passenger.PassengerDto;

public class PassengerDtoConverter implements Converter<Passenger, PassengerDto> {
    @Override
    public PassengerDto convert(Passenger from) {
        var passengerDto = new PassengerDto();

        passengerDto.setFirstName(from.getFirstName());
        passengerDto.setLastName(from.getLastName());
        passengerDto.setPatronymic(from.getPatronymic());
        passengerDto.setGender(from.getGender());
        passengerDto.setBirthDate(from.getBirthDate());

        return passengerDto;
    }
}
