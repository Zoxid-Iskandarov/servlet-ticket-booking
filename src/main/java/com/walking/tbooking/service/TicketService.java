package com.walking.tbooking.service;

import com.walking.tbooking.domain.ticket.Ticket;
import com.walking.tbooking.repository.TicketRepository;

import java.util.List;

public class TicketService {
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getTicketsByPassengerId(Long id) {
        return ticketRepository.findByPassengerId(id);
    }

    public List<Ticket> getActiveTicketsByPassengerId(Long id) {
        return ticketRepository.findActiveTicketsByPassengerId(id);
    }

    public Ticket create(Ticket ticket) {
        return ticketRepository.create(ticket);
    }

    public boolean cancelTicket(Long id) {
        return ticketRepository.markTicketAsCanceled(id);
    }
}
