package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.service.TicketService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TicketManagementServlet extends HttpServlet {
    private TicketService ticketService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.ticketService = (TicketService) config.getServletContext().getAttribute(
                ContextAttributeNames.TICKET_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tickets", ticketService.getTickets());

        req.getRequestDispatcher("/ticketManagement.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var passengerId = Long.parseLong(req.getParameter("passenger_id"));
            var flag = ticketService.cancelTicketByPassengerId(passengerId);
            if (flag) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter()
                        .write("Все билеты пассажира отменены");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter()
                        .write("Ошибка при отмене билетов пассажира");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID пассажира");
        }
    }
}
