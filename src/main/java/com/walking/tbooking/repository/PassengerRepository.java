package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.PassengerConverter;
import com.walking.tbooking.domain.passenger.Passenger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

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
            throw new RuntimeException("Ошибка при получении пассажиров по userId", e);
        }
    }

    private List<Passenger> findByUserId(Long userId, Connection connection) {
        var sql = """
                SELECT id, 
                       first_name, 
                       last_name, 
                       patronymic, 
                       gender, 
                       birth_date, 
                       passport_data, 
                       user_id
                FROM passenger 
                WHERE user_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            var rs = statement.executeQuery();

            return converter.convertMany(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пассажиров по userId", e);
        }
    }

    public Optional<Passenger> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пассажира по ID", e);
        }
    }

    private Optional<Passenger> findById(Long id, Connection connection) {
        var sql = """
                SELECT id, 
                       first_name, 
                       last_name, 
                       patronymic, 
                       gender, 
                       birth_date, 
                       passport_data, 
                       user_id
                FROM passenger
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пассажира по ID", e);
        }
    }

    public List<Passenger> findAll() {
        var sql = """
                SELECT id, 
                       first_name, 
                       last_name, 
                       patronymic, 
                       gender, 
                       birth_date, 
                       passport_data, 
                       user_id
                FROM passenger
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            var rs = statement.executeQuery();

            return converter.convertMany(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пассажиров", e);
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

            var generatedKeys = statement.getGeneratedKeys();
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

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(passenger, statement);
            statement.setLong(8, passenger.getId());
            statement.executeUpdate();

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении паражира", e);
        }
    }

    public boolean deleteById(Long id, Long userId) {
        var sql = """
                DELETE FROM passenger 
                WHERE id = ? AND user_id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.setLong(2, userId);
            return statement.executeUpdate() > 0;
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
