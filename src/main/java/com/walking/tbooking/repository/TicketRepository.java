package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.TicketConverter;
import com.walking.tbooking.domain.ticket.Ticket;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TicketRepository {
    private final DataSource dataSource;
    private final TicketConverter converter;

    public TicketRepository(DataSource dataSource, TicketConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public List<Ticket> findByPassengerId(Long passengerId) {
        try (Connection connection = dataSource.getConnection()) {
            return findByPassengerId(passengerId, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении билетов", e);
        }
    }

    private List<Ticket> findByPassengerId(Long passengerId, Connection connection) {
        var sql = """
                SELECT id, 
                       flight_id, 
                       passenger_id, 
                       seat_number, 
                       service_class, 
                       baggage_allowance, 
                       hand_baggage_allowance, 
                       is_canceled
                FROM ticket 
                WHERE passenger_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, passengerId);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении билетов", e);
        }
    }

    public List<Ticket> findActiveTicketsByPassengerId(Long id) {
        var sql = """
                SELECT ticket.id, 
                       ticket.flight_id, 
                       ticket.passenger_id, 
                       ticket.seat_number, 
                       ticket.service_class, 
                       ticket.baggage_allowance, 
                       ticket.hand_baggage_allowance, 
                       ticket.is_canceled
                FROM ticket 
                JOIN flight ON flight.id = ticket.flight_id
                WHERE ticket.passenger_id = ? 
                    AND flight.departure_date > now()
                    AND ticket.is_canceled = false
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении актуальных билетов", e);
        }
    }

    public Ticket create(Ticket ticket) {
        try (Connection connection = dataSource.getConnection()) {
            return create(ticket, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании билета", e);
        }
    }

    private Ticket create(Ticket ticket, Connection connection) {
        var sql = """
                INSERT INTO ticket (flight_id, 
                                    passenger_id, 
                                    seat_number, 
                                    service_class, 
                                    baggage_allowance, 
                                    hand_baggage_allowance) 
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(ticket, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticket.setId(generatedKeys.getLong("id"));
            }

            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании билета", e);
        }
    }

    public boolean markTicketAsCanceled(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return markTicketAsCanceled(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при отмене билета", e);
        }
    }

    private boolean markTicketAsCanceled(Long id, Connection connection) {
        var sql = """
                UPDATE ticket 
                SET is_canceled = true 
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при отмене билета", e);
        }
    }

    private void setParameters(Ticket ticket, PreparedStatement statement) throws SQLException {
        statement.setLong(1, ticket.getFlightId());
        statement.setLong(2, ticket.getPassengerId());
        statement.setString(3, ticket.getSeatNumber());
        statement.setString(4, ticket.getServiceClass().name());
        statement.setInt(5, ticket.getBaggageAllowance());
        statement.setInt(6, ticket.getHandBaggageAllowance());
    }
}
