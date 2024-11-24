package com.walking.tbooking.converter.dto.ticket;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.ticket.Ticket;
import com.walking.tbooking.model.ticket.TicketDto;

public class TicketDtoConverter implements Converter<Ticket, TicketDto> {
    @Override
    public TicketDto convert(Ticket from) {
        var ticketDto = new TicketDto();

        ticketDto.setFlightId(from.getFlightId());
        ticketDto.setPassengerId(from.getPassengerId());
        ticketDto.setSeatNumber(from.getSeatNumber());
        ticketDto.setServiceClass(from.getServiceClass());
        ticketDto.setBaggageAllowance(from.getBaggageAllowance());
        ticketDto.setHandBaggageAllowance(from.getHandBaggageAllowance());
        ticketDto.setCanceled(from.isCanceled());

        return ticketDto;
    }
}
