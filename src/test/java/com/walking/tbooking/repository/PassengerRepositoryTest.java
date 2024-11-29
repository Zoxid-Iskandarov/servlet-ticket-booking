package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.PassengerConverter;
import com.walking.tbooking.domain.passenger.Gender;
import com.walking.tbooking.domain.passenger.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerRepositoryTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private PassengerConverter converter;

    @InjectMocks
    private PassengerRepository repository;

    @Test
    void findByUserId_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Passenger> passengers = List.of(mock(Passenger.class));
        doReturn(passengers).when(converter).convertMany(rs);

        var actual = repository.findByUserId(1L);

        assertSame(passengers, actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
    }

    @Test
    void findByUserId_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Passenger> passengers = List.of();
        doReturn(passengers).when(converter).convertMany(rs);

        var actual = repository.findByUserId(1L);

        assertSame(passengers, actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
    }

    @Test
    void findByUserId_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.findByUserId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeQuery();
    }

    @Test
    void findByUserId_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeQuery();

        Executable executable = () -> repository.findByUserId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
    }

    @Test
    void findById_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        Optional<Passenger> passengerOptional = Optional.of(mock(Passenger.class));
        doReturn(passengerOptional).when(converter).convert(rs);

        var actual = repository.findById(1L);

        assertSame(passengerOptional, actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findById_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        Optional<Passenger> passengerOptional = Optional.empty();
        doReturn(passengerOptional).when(converter).convert(rs);

        var actual = repository.findById(1L);

        assertSame(passengerOptional, actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findById_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.findById(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeQuery();
    }

    @Test
    void findById_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeQuery();

        Executable executable = () -> repository.findById(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
    }

    @Test
    void findAll_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Passenger> passengers = List.of(mock(Passenger.class));
        doReturn(passengers).when(converter).convertMany(rs);

        var actual = repository.findAll();

        assertSame(passengers, actual);

        verify(dataSource).getConnection();
        verify(statement).executeQuery();
        verify(converter).convertMany(rs);
    }

    @Test
    void findAll_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Passenger> passengers = List.of();
        doReturn(passengers).when(converter).convertMany(rs);

        var actual = repository.findAll();

        assertSame(passengers, actual);

        verify(dataSource).getConnection();
        verify(statement).executeQuery();
        verify(converter).convertMany(rs);
    }

    @Test
    void findAll_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.findAll();

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).executeQuery();
    }

    @Test
    void findAll_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeQuery();

        Executable executable = () -> repository.findAll();

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).executeQuery();
    }

    @Test
    void create_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        var generatedKeys = mock(ResultSet.class);
        doReturn(generatedKeys).when(statement).getGeneratedKeys();

        doReturn(true).when(generatedKeys).next();
        doReturn(1L).when(generatedKeys).getLong(any());

        var passenger = getPassenger();

        Passenger actual = repository.create(passenger);

        assertSame(passenger, actual);
        assertEquals(passenger.getId(), actual.getId());

        verify(dataSource).getConnection();
        verify(statement, times(5)).setString(anyInt(), anyString());
        verify(statement).setDate(anyInt(), any());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void create_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.create(getPassenger());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setString(anyInt(), anyString());
        verify(statement, never()).setDate(anyInt(), any());
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void create_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.create(getPassenger());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(5)).setString(anyInt(), anyString());
        verify(statement).setDate(anyInt(), any());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void update_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var passenger = getPassenger(1L);

        var actual = repository.update(passenger);

        assertSame(passenger, actual);

        verify(dataSource).getConnection();
        verify(statement, times(5)).setString(anyInt(), anyString());
        verify(statement).setDate(anyInt(), any());
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void update_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.update(getPassenger());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setString(anyInt(), anyString());
        verify(statement, never()).setDate(anyInt(), any());
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void update_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.update(getPassenger(1L));

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(5)).setString(anyInt(), anyString());
        verify(statement).setDate(anyInt(), any());
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(1).when(statement).executeUpdate();

        var actual = repository.deleteById(1L, 1L);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(0).when(statement).executeUpdate();

        var actual = repository.deleteById(1L, 1L);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.deleteById(1L, 1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void deleteById_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.deleteById(1L, 1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    private Passenger getPassenger() {
        return getPassenger(null);
    }

    private Passenger getPassenger(Long id) {
        var passenger = new Passenger();

        passenger.setId(id);
        passenger.setFirstName("test");
        passenger.setLastName("test");
        passenger.setPatronymic("test");
        passenger.setGender(Gender.MALE);
        passenger.setBirthDate(LocalDate.now());
        passenger.setPassportData("test");
        passenger.setUserId(1L);

        return passenger;
    }
}