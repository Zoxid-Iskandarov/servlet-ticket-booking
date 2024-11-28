package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.UserConverter;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private UserConverter converter;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void findByEmail_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();

        Optional<User> userOptional = Optional.of(mock(User.class));
        doReturn(userOptional).when(converter).convert(rs);

        var actual = userRepository.findByEmail("test@gmail.com");

        assertEquals(userOptional, actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findByEmail_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();

        Optional<User> userOptional = Optional.empty();
        doReturn(userOptional).when(converter).convert(rs);

        var actual = userRepository.findByEmail("test@gmail.com");

        assertEquals(userOptional, actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findByEmail_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.findByEmail("test@gmail.com");

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).executeQuery();
        verify(converter, never()).convert(any());
    }

    @Test
    void findByEmail_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeQuery();

        Executable executable = () -> userRepository.findByEmail("test@gmail.com");

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
        verify(converter, never()).convert(any());
    }

    @Test
    void findByEmail_mappingError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();

        doThrow(SQLException.class).when(converter).convert(rs);

        Executable executable = () -> userRepository.findByEmail("test@gmail.com");

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void create_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(1L).when(rs).getLong("id");

        doReturn(1).when(preparedStatement).executeUpdate();
        doReturn(rs).when(preparedStatement).getGeneratedKeys();

        var user = getUser();

        var actual = userRepository.create(user);

        assertSame(user, actual);
        assertEquals(user.getId(), actual.getId());

        verify(rs).next();
        verify(dataSource).getConnection();
        verify(preparedStatement, times(6)).setString(anyInt(), anyString());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).getGeneratedKeys();
        verify(rs).getLong("id");
    }

    @Test
    void create_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.create(getUser());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).setString(anyInt(), anyString());
        verify(preparedStatement, never()).executeUpdate();
        verify(preparedStatement, never()).getGeneratedKeys();
    }

    @Test
    void create_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        var user = getUser();

        Executable executable = () -> userRepository.create(user);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, times(6)).setString(anyInt(), anyString());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement, never()).getGeneratedKeys();
    }

    @Test
    void update_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var user = getUser(1L);

        var actual = userRepository.update(user);

        assertSame(user, actual);
        assertEquals(user.getId(), actual.getId());

        verify(dataSource).getConnection();
        verify(preparedStatement, times(6)).setString(anyInt(), anyString());
        verify(preparedStatement).setLong(7, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void update_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        var user = getUser(1L);

        Executable executable = () -> userRepository.update(user);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).setString(anyInt(), anyString());
        verify(preparedStatement, never()).setLong(7, user.getId());
        verify(preparedStatement, never()).executeUpdate();
    }

    @Test
    void update_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        var user = getUser(1L);

        Executable executable = () -> userRepository.update(user);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, times(6)).setString(anyInt(), anyString());
        verify(preparedStatement).setLong(7, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void deleteById_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doReturn(1).when(preparedStatement).executeUpdate();

        var actual = userRepository.deleteById(1L);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void deleteById_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doReturn(0).when(preparedStatement).executeUpdate();

        var actual = userRepository.deleteById(1L);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void deleteById_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.deleteById(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).setLong(anyInt(), anyLong());
        verify(preparedStatement, never()).executeUpdate();
    }

    @Test
    void deleteById_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        Executable executable = () -> userRepository.deleteById(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void adminPresent_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();
        doReturn(true).when(rs).next();
        doReturn(1).when(rs).getInt(1);

        var actual = userRepository.adminPresent();

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
        verify(rs).next();
        verify(rs).getInt(1);
    }

    @Test
    void adminPresent_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();
        doReturn(false).when(rs).next();

        var actual = userRepository.adminPresent();

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
        verify(rs).next();
        verify(rs, never()).getInt(1);
    }

    @Test
    void adminPresent_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.adminPresent();

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).executeQuery();
    }

    @Test
    void adminPresent_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeQuery();

        Executable executable = () -> userRepository.adminPresent();

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).executeQuery();
    }

    @Test
    void updateLastLogin_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        userRepository.updateLastLogin(1L);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void updateLastLogin_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.updateLastLogin(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).setLong(anyInt(), anyLong());
        verify(preparedStatement, never()).executeUpdate();
    }

    @Test
    void updateLastLogin_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        Executable executable = () -> userRepository.updateLastLogin(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void updateStatus_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doReturn(1).when(preparedStatement).executeUpdate();

        var actual = userRepository.updateStatus(1L, false);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).setBoolean(anyInt(), anyBoolean());
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void updateStatus_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doReturn(0).when(preparedStatement).executeUpdate();

        var actual = userRepository.updateStatus(1L, false);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).setBoolean(anyInt(), anyBoolean());
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void updateStatus_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.updateStatus(1L, false);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).setBoolean(anyInt(), anyBoolean());
        verify(preparedStatement, never()).setLong(anyInt(), anyLong());
        verify(preparedStatement, never()).executeUpdate();
    }

    @Test
    void updateStatus_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        Executable executable = () -> userRepository.updateStatus(1L, false);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).setBoolean(anyInt(), anyBoolean());
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void isUserBlocked_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();

        doReturn(true).when(rs).next();
        doReturn(true).when(rs).getBoolean(any());

        var actual = userRepository.isUserBlocked(1L);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeQuery();
        verify(rs).next();
        verify(rs).getBoolean(any());
    }

    @Test
    void isUserBlocked_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(preparedStatement).executeQuery();

        doReturn(false).when(rs).next();

        Executable executable = () -> userRepository.isUserBlocked(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeQuery();
        verify(rs).next();
        verify(rs, never()).getBoolean(any());
    }

    @Test
    void isUserBlocked_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> userRepository.isUserBlocked(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement, never()).setLong(anyInt(), anyLong());
        verify(preparedStatement, never()).executeQuery();
    }

    @Test
    void isUserBlocked_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(preparedStatement).executeQuery();

        Executable executable = () -> userRepository.isUserBlocked(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(preparedStatement).setLong(anyInt(), anyLong());
        verify(preparedStatement).executeQuery();
    }

    private User getUser() {
        return getUser(null);
    }

    private User getUser(Long id) {
        User user = new User();

        user.setId(id);
        user.setEmail("test@example.com");
        user.setPasswordHash("test_password");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPatronymic("test");
        user.setRole(Role.USER);

        return user;
    }
}