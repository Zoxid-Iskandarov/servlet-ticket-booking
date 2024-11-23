package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.airport.Airport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirportConverter implements ResultSetConverter<Optional<Airport>> {
    @Override
    public Optional<Airport> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapToRow(rs)) : Optional.empty();
    }

    public List<Airport> convertMany(ResultSet rs) throws SQLException {
        var airports = new ArrayList<Airport>();

        while (rs.next()) {
            airports.add(mapToRow(rs));
        }

        return airports;
    }

    private Airport mapToRow(ResultSet rs) throws SQLException {
        var airport = new Airport();

        airport.setId(rs.getInt("id"));
        airport.setCode(rs.getString("code"));
        airport.setName(rs.getString("name"));
        airport.setAddress(rs.getString("address"));

        return airport;
    }
}
