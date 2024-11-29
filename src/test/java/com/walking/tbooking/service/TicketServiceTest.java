package com.walking.tbooking.service;

import com.walking.tbooking.domain.ticket.ServiceClass;
import com.walking.tbooking.domain.ticket.Ticket;
import com.walking.tbooking.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void getTickets_value_success() {
        List<Ticket> tickets = List.of(mock(Ticket.class));
        doReturn(tickets).when(ticketRepository).findAll();

        var actual = ticketService.getTickets();

        assertFalse(actual.isEmpty());
        assertEquals(tickets, actual);

        verify(ticketRepository).findAll();
    }

    @Test
    void getTickets_empty_success() {
        List<Ticket> tickets = List.of();
        doReturn(tickets).when(ticketRepository).findAll();

        var actual = ticketService.getTickets();

        assertTrue(actual.isEmpty());
        assertEquals(tickets, actual);

        verify(ticketRepository).findAll();
    }

    @Test
    void getTickets_repositoryError_failed() {
        doThrow(RuntimeException.class).when(ticketRepository).findAll();

        Executable executable = () -> ticketService.getTickets();

        assertThrows(RuntimeException.class, executable);

        verify(ticketRepository).findAll();
    }

    @Test
    void getTicketsByPassengerId_value_success() {
        List<Ticket> tickets = List.of(mock(Ticket.class));
        doReturn(tickets).when(ticketRepository).findByPassengerId(1L);

        var actual = ticketService.getTicketsByPassengerId(1L);

        assertFalse(actual.isEmpty());
        assertEquals(tickets, actual);

        verify(ticketRepository).findByPassengerId(1L);
    }

    @Test
    void getTicketsByPassengerId_empty_success() {
        List<Ticket> tickets = List.of();
        doReturn(tickets).when(ticketRepository).findByPassengerId(1L);

        var actual = ticketService.getTicketsByPassengerId(1L);

        assertTrue(actual.isEmpty());
        assertEquals(tickets, actual);

        verify(ticketRepository).findByPassengerId(1L);
    }

    @Test
    void getTicketsByPassengerId_repositoryError_failed() {
        doThrow(RuntimeException.class).when(ticketRepository).findByPassengerId(1L);

        Executable executable = () -> ticketService.getTicketsByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(ticketRepository).findByPassengerId(anyLong());
    }

    @Test
    void getActiveTicketsByPassengerId_value_success() {
        List<Ticket> tickets = List.of(mock(Ticket.class));
        doReturn(tickets).when(ticketRepository).findActiveTicketsByPassengerId(1L);

        var actual = ticketService.getActiveTicketsByPassengerId(1L);

        assertFalse(actual.isEmpty());
        assertEquals(tickets, actual);

        verify(ticketRepository).findActiveTicketsByPassengerId(1L);
    }

    @Test
    void getActiveTicketsByPassengerId_empty_success() {
        List<Ticket> tickets = List.of();
        doReturn(tickets).when(ticketRepository).findActiveTicketsByPassengerId(1L);

        var actual = ticketService.getActiveTicketsByPassengerId(1L);

        assertTrue(actual.isEmpty());
        assertEquals(tickets, actual);

        verify(ticketRepository).findActiveTicketsByPassengerId(1L);
    }

    @Test
    void getActiveTicketsByPassengerId_repositoryError_failed() {
        doThrow(RuntimeException.class).when(ticketRepository).findActiveTicketsByPassengerId(1L);

        Executable executable = () -> ticketService.getActiveTicketsByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);
        verify(ticketRepository).findActiveTicketsByPassengerId(anyLong());
    }

    @Test
    void create_success() {
        var ticket = getTicket();
        doReturn(ticket).when(ticketRepository).create(ticket);

        var actual = ticketService.create(ticket);

        assertSame(ticket, actual);
        assertEquals(ticket.getId(), actual.getId());

        verify(ticketRepository).create(ticket);
    }

    @Test
    void create_repositoryError_failed() {
        var ticket = getTicket();
        doThrow(RuntimeException.class).when(ticketRepository).create(ticket);

        Executable executable = () -> ticketService.create(ticket);

        assertThrows(RuntimeException.class, executable);
        verify(ticketRepository).create(ticket);
    }

    @Test
    void cancelTicket_success() {
        doReturn(true).when(ticketRepository).markTicketAsCanceled(1L);

        var actual = ticketService.cancelTicket(1L);

        assertTrue(actual);
        verify(ticketRepository).markTicketAsCanceled(1L);
    }

    @Test
    void cancelTicket_failed() {
        doReturn(false).when(ticketRepository).markTicketAsCanceled(1L);

        var actual = ticketService.cancelTicket(1L);

        assertFalse(actual);
        verify(ticketRepository).markTicketAsCanceled(1L);
    }

    @Test
    void cancelTicket_repositoryError_failed() {
        doThrow(RuntimeException.class).when(ticketRepository).markTicketAsCanceled(1L);

        Executable executable = () -> ticketService.cancelTicket(1L);

        assertThrows(RuntimeException.class, executable);
        verify(ticketRepository).markTicketAsCanceled(1L);
    }

    @Test
    void cancelTicketByPassengerId_success() {
        doReturn(true).when(ticketRepository).cancelTicketsByPassengerId(1L);

        var actual = ticketService.cancelTicketByPassengerId(1L);

        assertTrue(actual);
        verify(ticketRepository).cancelTicketsByPassengerId(1L);
    }

    @Test
    void cancelTicketByPassengerId_failed() {
        doReturn(false).when(ticketRepository).cancelTicketsByPassengerId(1L);

        var actual = ticketService.cancelTicketByPassengerId(1L);

        assertFalse(actual);
        verify(ticketRepository).cancelTicketsByPassengerId(1L);
    }

    @Test
    void cancelTicketByPassengerId_repositoryError_failed() {
        doThrow(RuntimeException.class).when(ticketRepository).cancelTicketsByPassengerId(1L);

        Executable executable = () -> ticketService.cancelTicketByPassengerId(1L);

        assertThrows(RuntimeException.class, executable);
        verify(ticketRepository).cancelTicketsByPassengerId(1L);
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