package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.FlightConverter;
import com.walking.tbooking.domain.flight.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightRepositoryTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private FlightConverter converter;

    @InjectMocks
    private FlightRepository repository;

    @Test
    void findAll_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Flight> flights = List.of(mock(Flight.class));
        doReturn(flights).when(converter).convertMany(rs);

        var actual = repository.findAll();

        assertSame(flights, actual);
        assertFalse(actual.isEmpty());

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

        List<Flight> flights = List.of();
        doReturn(flights).when(converter).convertMany(rs);

        var actual = repository.findAll();

        assertSame(flights, actual);
        assertTrue(actual.isEmpty());

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
    void findById_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        Optional<Flight> flightOptional = Optional.of(mock(Flight.class));
        doReturn(flightOptional).when(converter).convert(rs);

        var actual = repository.findById(1L);

        assertSame(flightOptional, actual);
        assertFalse(actual.isEmpty());

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

        Optional<Flight> flightOptional = Optional.empty();
        doReturn(flightOptional).when(converter).convert(rs);

        var actual = repository.findById(1L);

        assertSame(flightOptional, actual);
        assertTrue(actual.isEmpty());

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
    void create_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        var generatedKeys = mock(ResultSet.class);
        doReturn(generatedKeys).when(statement).getGeneratedKeys();

        doReturn(true).when(generatedKeys).next();
        doReturn(1L).when(generatedKeys).getLong(anyString());

        var flight = getFlight();

        var actual = repository.create(flight);

        assertSame(flight, actual);
        assertEquals(flight.getId(), actual.getId());

        verify(dataSource).getConnection();
        verify(statement, times(2)).setTimestamp(anyInt(), any(Timestamp.class));
        verify(statement, times(4)).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    @Test
    void create_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        var flight = getFlight();

        Executable executable = () -> repository.create(flight);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setTimestamp(anyInt(), any(Timestamp.class));
        verify(statement, never()).setInt(anyInt(), anyInt());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void create_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        doThrow(SQLException.class).when(statement).executeUpdate();

        var flight = getFlight();

        Executable executable = () -> repository.create(flight);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(2)).setTimestamp(anyInt(), any(Timestamp.class));
        verify(statement, times(4)).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    @Test
    void update_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var flight = getFlight(1L);

        var actual = repository.update(flight);

        assertSame(flight, actual);
        assertEquals(flight.getId(), actual.getId());

        verify(dataSource).getConnection();
        verify(statement, times(2)).setTimestamp(anyInt(), any(Timestamp.class));
        verify(statement, times(4)).setInt(anyInt(), anyInt());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void update_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.update(getFlight(1L));

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setTimestamp(anyInt(), any(Timestamp.class));
        verify(statement, never()).setInt(anyInt(), anyInt());
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void update_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var flight = getFlight(1L);

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.update(flight);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(2)).setTimestamp(anyInt(), any(Timestamp.class));
        verify(statement, times(4)).setInt(anyInt(), anyInt());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(1).when(statement).executeUpdate();

        var actual = repository.deleteById(1L);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(0).when(statement).executeUpdate();

        var actual = repository.deleteById(1L);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.deleteById(1L);

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

        Executable executable = () -> repository.deleteById(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    private Flight getFlight() {
        return getFlight(null);
    }

    private Flight getFlight(Long id) {
        var flight = new Flight();

        flight.setId(id);
        flight.setDepartureDate(LocalDateTime.now());
        flight.setArrivalDate(LocalDateTime.now());
        flight.setDepartureAirportId(1);
        flight.setArrivalAirportId(2);
        flight.setTotalSeats(120);
        flight.setAvailableSeats(150);

        return flight;
    }
}