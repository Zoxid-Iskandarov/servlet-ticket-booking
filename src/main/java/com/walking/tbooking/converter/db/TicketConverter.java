package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.ticket.ServiceClass;
import com.walking.tbooking.domain.ticket.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketConverter implements ResultSetConverter<List<Ticket>> {
    @Override
    public List<Ticket> convert(ResultSet rs) throws SQLException {
        var tickets = new ArrayList<Ticket>();

        while (rs.next()) {
            tickets.add(mapToRow(rs));
        }

        return tickets;
    }

    private Ticket mapToRow(ResultSet rs) throws SQLException {
        var ticket = new Ticket();

        ticket.setId(rs.getLong("id"));
        ticket.setFlightId(rs.getLong("flight_id"));
        ticket.setPassengerId(rs.getLong("passenger_id"));
        ticket.setSeatNumber(rs.getString("seat_number").trim());
        ticket.setServiceClass(ServiceClass.fromString(rs.getString("service_class"))
                .orElseThrow(() -> new IllegalArgumentException("Invalid service class")));
        ticket.setBaggageAllowance(rs.getInt("baggage_allowance"));
        ticket.setHandBaggageAllowance(rs.getInt("hand_baggage_allowance"));
        ticket.setCanceled(rs.getBoolean("is_canceled"));

        return ticket;
    }
}
