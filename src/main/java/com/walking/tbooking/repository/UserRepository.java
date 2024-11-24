package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.UserConverter;
import com.walking.tbooking.domain.users.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class UserRepository {
    private final DataSource dataSource;
    private final UserConverter converter;

    public UserRepository(DataSource dataSource, UserConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<User> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            return findByEmail(email, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователя", e);
        }
    }

    public Optional<User> findByEmail(String email, Connection connection) {
        var sql = """
                SELECT id, email, password_hash, first_name, last_name, patronymic, role, last_login, is_blocked
                FROM \"user\" WHERE email = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователя", e);
        }
    }

    public User create(User user) {
        try (Connection connection = dataSource.getConnection()) {
            return create(user, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании пользователя", e);
        }
    }

    public User create(User user, Connection connection) {
        var sql = """
                INSERT INTO \"user\" (email, password_hash, first_name, last_name, patronymic, role)
                VALUES (?, ?, ?, ?, ?, ?) 
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(user, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании пользователя", e);
        }
    }

    public User update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            return update(user, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении пользователя", e);
        }
    }

    public User update(User user, Connection connection) {
        var sql = """
                UPDATE \"user\" 
                    SET email = ?,
                        password_hash = ?,
                        first_name = ?,
                        last_name = ?,
                        patronymic = ?,
                        role = ?
                    WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(user, statement);
            statement.setLong(7, user.getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении пользователя", e);
        }
    }

    public boolean deleteById(Long id) {
        var sql = """
                DELETE FROM \"user\" WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }
    }

    public boolean adminExists() {
        var sql = """
                SELECT count(*) FROM \"user\" WHERE role = 'ADMIN'
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке существования администратора", e);
        }

        return false;
    }

    public void updateLastLogin(Long id) {
        var sql = """
                UPDATE \"user\" SET last_login = now() WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении информации о последнем входе", e);
        }
    }

    public boolean updateIsBlockedById(Long id, boolean isBlocked) {
        var sql = """
                UPDATE \"user\" 
                SET is_blocked = ? 
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, isBlocked);
            statement.setLong(2, id);
            var rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при блокировке пользователя", e);
        }
    }

    public boolean isUserBlocked(Long id) {
        var sql = """
                SELECT is_blocked 
                FROM \"user\" 
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            rs.next();
            return rs.getBoolean("is_blocked");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении информации о блокировке", e);
        }
    }

    private void setParameters(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPasswordHash());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getPatronymic());
        statement.setString(6, user.getRole().name());
    }
}
