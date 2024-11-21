package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.passenger.Gender;
import com.walking.tbooking.domain.passenger.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerConverter implements ResultSetConverter<List<Passenger>> {
    @Override
    public List<Passenger> convert(ResultSet rs) throws SQLException {
        var passengers = new ArrayList<Passenger>();

        while (rs.next()) {
            passengers.add(mapToRow(rs));
        }

        return passengers;
    }

    private Passenger mapToRow(ResultSet rs) throws SQLException {
        var passenger = new Passenger();

        passenger.setId(rs.getLong("id"));
        passenger.setFirstName(rs.getString("first_name"));
        passenger.setLastName(rs.getString("last_name"));
        passenger.setPatronymic(rs.getString("patronymic"));
        passenger.setGender(Gender.fromString(rs.getString("gender").trim())
                .orElseThrow(() -> new IllegalArgumentException("Invalid gender")));
        passenger.setBirthDate(rs.getDate("birth_date").toLocalDate());
        passenger.setPassportData(rs.getString("passport_data"));
        passenger.setUserId(rs.getLong("user_id"));

        return passenger;
    }
}
