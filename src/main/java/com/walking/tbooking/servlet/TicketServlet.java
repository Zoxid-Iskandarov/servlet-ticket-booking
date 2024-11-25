package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.ticket.TicketDtoConverter;
import com.walking.tbooking.converter.dto.ticket.request.CreateTicketRequestConverter;
import com.walking.tbooking.filter.RequestJsonDeserializerFilter;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.model.ticket.request.CreateTicketRequest;
import com.walking.tbooking.service.TicketService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TicketServlet extends HttpServlet {
    private TicketService ticketService;

    private TicketDtoConverter ticketDtoConverter;
    private CreateTicketRequestConverter createTicketRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.ticketService = (TicketService) servletContext.getAttribute(ContextAttributeNames.TICKET_SERVICE);

        this.ticketDtoConverter = (TicketDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.TICKET_DTO_CONVERTER);
        this.createTicketRequestConverter = (CreateTicketRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_TICKET_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var id = Long.parseLong(req.getParameter("id"));

            req.setAttribute("tickets", ticketService.getTicketsByPassengerId(id));
            req.setAttribute("activeTickets", ticketService.getActiveTicketsByPassengerId(id));

            req.getRequestDispatcher("ticket.jsp")
                    .forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID пассажира");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var ticketRequest = (CreateTicketRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var ticket = createTicketRequestConverter.convert(ticketRequest);
        var createdTicket = ticketService.create(ticket);
        var ticketDto = ticketDtoConverter.convert(createdTicket);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, ticketDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var id = Long.parseLong(req.getParameter("id"));
            var flag = ticketService.cancelTicket(id);

            if (flag) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter()
                        .write("Билет отменен");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter()
                        .write("Билет не найден");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID билета");
        }
    }
}
