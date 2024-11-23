package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.AirportConverter;
import com.walking.tbooking.domain.airport.Airport;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AirportRepository {
    private final DataSource dataSource;
    private final AirportConverter converter;

    public AirportRepository(DataSource dataSource, AirportConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public List<Airport> findAll() {
        var sql = """
                SELECT id, code, name, address
                FROM airport
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            return converter.convertMany(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении аэропортов", e);
        }
    }

    public Optional<Airport> findById(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении аэропорта", e);
        }
    }

    public Optional<Airport> findById(Integer id, Connection connection) {
        var sql = """
                SELECT id, code, name, address
                FROM airport WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении аэропорта", e);
        }
    }

    public Airport create(Airport airport) {
        try (Connection connection = dataSource.getConnection()) {
            return create(airport, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании аэропорта", e);
        }
    }

    public Airport create(Airport airport, Connection connection) {
        var sql = """
                INSERT INTO airport (code, name, address)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(airport, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                airport.setId(generatedKeys.getInt("id"));
            }

            return airport;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании аэропорта", e);
        }
    }

    public Airport update(Airport airport) {
        try (Connection connection = dataSource.getConnection()) {
            return update(airport, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании аэропорта", e);
        }
    }

    private Airport update(Airport airport, Connection connection) {
        var sql = """
                UPDATE airport 
                    SET code = ?,
                        name = ?,
                        address = ?
                    WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(airport, statement);
            statement.setInt(4, airport.getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                airport.setId(generatedKeys.getInt("id"));
            }

            return airport;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании аэропорта", e);
        }
    }

    public boolean deleteById(Integer id) {
        var sql = """
                DELETE FROM airport WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении аэропорта", e);
        }
    }

    private void setParameters(Airport airport, PreparedStatement statement) throws SQLException {
        statement.setString(1, airport.getCode());
        statement.setString(2, airport.getName());
        statement.setString(3, airport.getAddress());
    }
}
