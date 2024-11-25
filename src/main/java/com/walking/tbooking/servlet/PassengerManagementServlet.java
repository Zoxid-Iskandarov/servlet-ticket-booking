package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.service.PassengerService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PassengerManagementServlet extends HttpServlet {
    private PassengerService passengerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.passengerService = (PassengerService) config.getServletContext().getAttribute(
                ContextAttributeNames.PASSENGER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("passenger", passengerService.getPassengerById(id));
        } catch (NumberFormatException e) {
            req.setAttribute("passengers", passengerService.getPassengers());
        }

        req.getRequestDispatcher("/passengerManagement.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var userId = Long.parseLong(req.getParameter("userId"));
            var passengers = passengerService.getPassengersByUserId(userId);

            req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, passengers);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID пользователя");
        }
    }
}
