package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.PassengerConverter;
import com.walking.tbooking.domain.passenger.Passenger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PassengerRepository {
    private final DataSource dataSource;
    private final PassengerConverter converter;

    public PassengerRepository(DataSource dataSource, PassengerConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public List<Passenger> findByUserId(Long userId) {
        try (Connection connection = dataSource.getConnection()) {
            return findByUserId(userId, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении паражира", e);
        }
    }

    public List<Passenger> findByUserId(Long userId, Connection connection) {
        var sql = """
                SELECT id, first_name, last_name, patronymic, gender, birth_date, passport_data, user_id
                FROM passenger WHERE user_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении паражира", e);
        }
    }

    public Passenger create(Passenger passenger) {
        try (Connection connection = dataSource.getConnection()) {
            return create(passenger, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании паражира", e);
        }
    }

    public Passenger create(Passenger passenger, Connection connection) {
        var sql = """
                INSERT INTO passenger (first_name, last_name, patronymic, gender, birth_date, passport_data, user_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?) 
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(passenger, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                passenger.setId(generatedKeys.getLong("id"));
            }

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании паражира", e);
        }
    }

    public Passenger update(Passenger passenger) {
        try (Connection connection = dataSource.getConnection()) {
            return update(passenger, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении паражира", e);
        }
    }

    public Passenger update(Passenger passenger, Connection connection) {
        var sql = """
                UPDATE passenger 
                    SET first_name = ?, 
                        last_name = ?, 
                        patronymic = ?, 
                        gender = ?, 
                        birth_date = ?, 
                        passport_data = ?, 
                        user_id = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(passenger, statement);
            statement.setLong(8, passenger.getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                passenger.setId(generatedKeys.getLong("id"));
            }

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении паражира", e);
        }
    }

    public void deleteById(Long id) {
        var sql = """
                DELETE FROM passenger WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пассажира", e);
        }
    }

    private void setParameters(Passenger passenger, PreparedStatement statement) throws SQLException {
        statement.setString(1, passenger.getFirstName());
        statement.setString(2, passenger.getLastName());
        statement.setString(3, passenger.getPatronymic());
        statement.setString(4, passenger.getGender().name());
        statement.setDate(5, Date.valueOf(passenger.getBirthDate()));
        statement.setString(6, passenger.getPassportData());
        statement.setLong(7, passenger.getUserId());
    }
}
