package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.FlightConverter;
import com.walking.tbooking.domain.flight.Flight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class FlightRepository {
    private final DataSource dataSource;
    private final FlightConverter converter;

    public FlightRepository(DataSource dataSource, FlightConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public List<Flight> findAll() {
        var sql = """
                SELECT id, 
                    departure_date, 
                    arrival_date, 
                    departure_airport_id, 
                    arrival_airport_id, 
                    total_seats, 
                    available_seats
                FROM flight 
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            var rs = statement.executeQuery();
            return converter.convertMany(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении рейсов", e);
        }
    }

    public Optional<Flight> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении рейса", e);
        }
    }

    private Optional<Flight> findById(Long id, Connection connection) {
        var sql = """
                SELECT id, 
                    departure_date, 
                    arrival_date, 
                    departure_airport_id, 
                    arrival_airport_id, 
                    total_seats, 
                    available_seats
                FROM flight 
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            var rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении рейса", e);
        }
    }

    public Flight create(Flight flight) {
        try (Connection connection = dataSource.getConnection()) {
            return create(flight, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании рейса", e);
        }
    }

    private Flight create(Flight flight, Connection connection) {
        var sql = """
                INSERT INTO flight (departure_date, arrival_date, departure_airport_id, arrival_airport_id, total_seats, available_seats)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(flight, statement);
            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                flight.setId(generatedKeys.getLong("id"));
            }

            return flight;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании рейса", e);
        }
    }

    public Flight update(Flight flight) {
        try (Connection connection = dataSource.getConnection()) {
            return update(flight, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении рейса", e);
        }
    }

    private Flight update(Flight flight, Connection connection) {
        var sql = """
                UPDATE flight 
                    SET departure_date = ?,
                        arrival_date = ?,
                        departure_airport_id = ?,
                        arrival_airport_id = ?,
                        total_seats = ?,
                        available_seats = ?
                    WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(flight, statement);
            statement.setLong(7, flight.getId());
            statement.executeUpdate();

            return flight;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении рейса", e);
        }
    }

    public boolean deleteById(Long id) {
        var sql = """
                DELETE FROM flight 
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении рейса", e);
        }
    }

    private void setParameters(Flight flight, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(flight.getDepartureDate()));
        statement.setTimestamp(2, Timestamp.valueOf(flight.getArrivalDate()));
        statement.setInt(3, flight.getDepartureAirportId());
        statement.setInt(4, flight.getArrivalAirportId());
        statement.setInt(5, flight.getTotalSeats());
        statement.setInt(6, flight.getAvailableSeats());
    }
}
