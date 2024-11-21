package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserConverter implements ResultSetConverter<Optional<User>> {
    @Override
    public Optional<User> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapToRow(rs)) : Optional.empty();
    }

    private User mapToRow(ResultSet rs) throws SQLException {
        var user = new User();

        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPatronymic(rs.getString("patronymic"));
        user.setRole(Role.fromString(rs.getString("role").trim())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role")));
        user.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());
        user.setBlocked(rs.getBoolean("is_blocked"));

        return user;
    }
}
