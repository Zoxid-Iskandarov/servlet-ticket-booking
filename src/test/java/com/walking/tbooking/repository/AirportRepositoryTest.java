package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.AirportConverter;
import com.walking.tbooking.domain.airport.Airport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportRepositoryTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private AirportConverter converter;

    @InjectMocks
    private AirportRepository repository;

    @Test
    void findAll_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Airport> airports = List.of(mock(Airport.class));
        doReturn(airports).when(converter).convertMany(rs);

        var actual = repository.findAll();

        assertEquals(airports, actual);

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

        List<Airport> airports = List.of();
        doReturn(airports).when(converter).convertMany(rs);

        var actual = repository.findAll();

        assertEquals(airports, actual);

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

        Optional<Airport> airportOptional = Optional.of(mock(Airport.class));
        doReturn(airportOptional).when(converter).convert(rs);

        var actual = repository.findById(1);

        assertEquals(airportOptional, actual);
        assertTrue(actual.isPresent());

        verify(dataSource).getConnection();
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findById_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        Optional<Airport> airportOptional = Optional.empty();
        doReturn(airportOptional).when(converter).convert(rs);

        var actual = repository.findById(1);

        assertEquals(airportOptional, actual);
        assertTrue(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findById_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.findById(1);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setInt(anyInt(), anyInt());
        verify(statement, never()).executeQuery();
    }

    @Test
    void findById_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeQuery();

        Executable executable = () -> repository.findById(1);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeQuery();
    }

    @Test
    void create_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        var generatedKeys = mock(ResultSet.class);
        doReturn(generatedKeys).when(statement).getGeneratedKeys();

        doReturn(true).when(generatedKeys).next();
        doReturn(1).when(generatedKeys).getInt(anyString());

        var airport = getAirport();

        var actual = repository.create(airport);

        assertSame(airport, actual);
        assertEquals(airport.getId(), actual.getId());

        verify(dataSource).getConnection();
        verify(statement, times(3)).setString(anyInt(), anyString());
        verify(statement).executeUpdate();
    }

    @Test
    void create_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.create(getAirport());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setString(anyInt(), anyString());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void create_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.create(getAirport());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(3)).setString(anyInt(), anyString());
        verify(statement).executeUpdate();
    }

    @Test
    void update_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var airport = getAirport(1);

        var actual = repository.update(airport);

        assertSame(airport, actual);

        verify(dataSource).getConnection();
        verify(statement, times(3)).setString(anyInt(), anyString());
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    @Test
    void update_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        var airport = getAirport(1);

        Executable executable = () -> repository.update(airport);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setString(anyInt(), anyString());
        verify(statement, never()).setInt(anyInt(), anyInt());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void update_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeUpdate();

        var airport = getAirport(1);

        Executable executable = () -> repository.update(airport);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(3)).setString(anyInt(), anyString());
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(1).when(statement).executeUpdate();

        var actual = repository.deleteById(1);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(0).when(statement).executeUpdate();

        var actual = repository.deleteById(1);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    @Test
    void deleteById_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.deleteById(1);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setInt(anyInt(), anyInt());
        verify(statement, never()).executeUpdate();
    }

    @Test
    void deleteById_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.deleteById(1);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
    }

    private Airport getAirport() {
        return getAirport(null);
    }

    private Airport getAirport(Integer id) {
        var airport = new Airport();

        airport.setId(id);
        airport.setCode("TestCode");
        airport.setName("TestName");
        airport.setAddress("TestAddress");

        return airport;
    }
}