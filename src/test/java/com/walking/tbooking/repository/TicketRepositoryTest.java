package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.TicketConverter;
import com.walking.tbooking.domain.ticket.ServiceClass;
import com.walking.tbooking.domain.ticket.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketRepositoryTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private TicketConverter converter;

    @InjectMocks
    private TicketRepository repository;

    @Test
    void findAll_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Ticket> tickets = List.of(mock(Ticket.class));
        doReturn(tickets).when(converter).convert(rs);

        var actual = repository.findAll();

        assertSame(tickets, actual);
        assertFalse(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findAll_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Ticket> tickets = List.of();
        doReturn(tickets).when(converter).convert(rs);

        var actual = repository.findAll();

        assertSame(tickets, actual);
        assertTrue(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).executeQuery();
        verify(converter).convert(rs);
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
    void findByPassengerId_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Ticket> tickets = List.of(mock(Ticket.class));
        doReturn(tickets).when(converter).convert(rs);

        var actual = repository.findByPassengerId(1L);

        assertSame(tickets, actual);
        assertFalse(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findByPassengerId_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Ticket> tickets = List.of();
        doReturn(tickets).when(converter).convert(rs);

        var actual = repository.findByPassengerId(1L);

        assertSame(tickets, actual);
        assertTrue(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findByPassengerId_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.findByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeQuery();
    }

    @Test
    void findByPassengerId_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeQuery();

        Executable executable = () -> repository.findByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
    }

    @Test
    void findActiveTicketsByPassengerId_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Ticket> tickets = List.of(mock(Ticket.class));
        doReturn(tickets).when(converter).convert(rs);

        var actual = repository.findActiveTicketsByPassengerId(1L);

        assertSame(tickets, actual);
        assertFalse(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findActiveTicketsByPassengerId_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        var rs = mock(ResultSet.class);
        doReturn(rs).when(statement).executeQuery();

        List<Ticket> tickets = List.of();
        doReturn(tickets).when(converter).convert(rs);

        var actual = repository.findActiveTicketsByPassengerId(1L);

        assertSame(tickets, actual);
        assertTrue(actual.isEmpty());

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();
        verify(converter).convert(rs);
    }

    @Test
    void findActiveTicketsByPassengerId_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.findActiveTicketsByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).executeQuery();
    }

    @Test
    void findActiveTicketsByPassengerId_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeQuery();

        Executable executable = () -> repository.findActiveTicketsByPassengerId(1L);

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

        var ticket = getTicket();

        var actual = repository.create(ticket);

        assertSame(ticket, actual);
        assertEquals(ticket.getId(), actual.getId());

        verify(dataSource).getConnection();
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement, times(2)).setString(anyInt(), anyString());
        verify(statement, times(2)).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
        verify(statement).getGeneratedKeys();
        verify(generatedKeys).next();
        verify(generatedKeys).getLong(anyString());
    }

    @Test
    void create_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.create(getTicket());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).setString(anyInt(), anyString());
        verify(statement, never()).setInt(anyInt(), anyInt());
        verify(statement, never()).executeUpdate();
        verify(statement, never()).getGeneratedKeys();
    }

    @Test
    void create_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.create(getTicket());

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement, times(2)).setString(anyInt(), anyString());
        verify(statement, times(2)).setInt(anyInt(), anyInt());
        verify(statement).executeUpdate();
        verify(statement, never()).getGeneratedKeys();
    }

    @Test
    void markTicketAsCanceled_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(1).when(statement).executeUpdate();

        var actual = repository.markTicketAsCanceled(1L);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void markTicketAsCanceled_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(0).when(statement).executeUpdate();

        var actual = repository.markTicketAsCanceled(1L);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void markTicketAsCanceled_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.markTicketAsCanceled(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).setString(anyInt(), anyString());
    }

    @Test
    void markTicketAsCanceled_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.markTicketAsCanceled(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void cancelTicketsByPassengerId_value_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(1).when(statement).executeUpdate();

        var actual = repository.cancelTicketsByPassengerId(1L);

        assertTrue(actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void cancelTicketsByPassengerId_empty_success() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doReturn(0).when(statement).executeUpdate();

        var actual = repository.cancelTicketsByPassengerId(1L);

        assertFalse(actual);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    @Test
    void cancelTicketsByPassengerId_dbUnavailable_failed() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();

        Executable executable = () -> repository.cancelTicketsByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement, never()).setLong(anyInt(), anyLong());
        verify(statement, never()).setString(anyInt(), anyString());
    }

    @Test
    void cancelTicketsByPassengerId_executeError_failed() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        doReturn(statement).when(connection).prepareStatement(any());

        doThrow(SQLException.class).when(statement).executeUpdate();

        Executable executable = () -> repository.cancelTicketsByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(dataSource).getConnection();
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeUpdate();
    }

    private Ticket getTicket() {
        return getTicket(null);
    }

    private Ticket getTicket(Long id) {
        var ticket = new Ticket();

        ticket.setId(id);
        ticket.setFlightId(1L);
        ticket.setPassengerId(1L);
        ticket.setSeatNumber("A1");
        ticket.setServiceClass(ServiceClass.ECONOMY);
        ticket.setBaggageAllowance(10);
        ticket.setHandBaggageAllowance(10);

        return ticket;
    }
}