package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.flight.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightConverter implements ResultSetConverter<Optional<Flight>> {
    @Override
    public Optional<Flight> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapToRow(rs)) : Optional.empty();
    }

    public List<Flight> convertMany(ResultSet rs) throws SQLException {
        var flights = new ArrayList<Flight>();

        while (rs.next()) {
            flights.add(mapToRow(rs));
        }

        return flights;
    }

    private Flight mapToRow(ResultSet rs) throws SQLException {
        var flight = new Flight();

        flight.setId(rs.getLong("id"));
        flight.setDepartureDate(rs.getTimestamp("departure_date").toLocalDateTime());
        flight.setArrivalDate(rs.getTimestamp("arrival_date").toLocalDateTime());
        flight.setDepartureAirportId(rs.getInt("departure_airport_id"));
        flight.setArrivalAirportId(rs.getInt("arrival_airport_id"));
        flight.setTotalSeats(rs.getInt("total_seats"));
        flight.setAvailableSeats(rs.getInt("available_seats"));

        return flight;
    }
}
