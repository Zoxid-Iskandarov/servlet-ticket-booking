package com.walking.tbooking.converter.dto.ticket.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.ticket.Ticket;
import com.walking.tbooking.model.ticket.request.CreateTicketRequest;

public class CreateTicketRequestConverter implements Converter<CreateTicketRequest, Ticket> {
    @Override
    public Ticket convert(CreateTicketRequest from) {
        var ticket = new Ticket();

        ticket.setFlightId(from.getFlightId());
        ticket.setPassengerId(from.getPassengerId());
        ticket.setSeatNumber(from.getSeatNumber());
        ticket.setServiceClass(from.getServiceClass());
        ticket.setBaggageAllowance(from.getBaggageAllowance());
        ticket.setHandBaggageAllowance(from.getHandBaggageAllowance());

        return ticket;
    }
}
