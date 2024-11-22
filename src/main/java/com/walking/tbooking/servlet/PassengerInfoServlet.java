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

public class PassengerInfoServlet extends HttpServlet {
    private PassengerService passengerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.passengerService = (PassengerService) config.getServletContext().getAttribute(
                ContextAttributeNames.PASSENGER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("passengers", passengerService.getPassengers());

        req.getRequestDispatcher("/passengerInfo.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = Long.valueOf(req.getParameter("userId"));

        var passengers = passengerService.getPassengersByUserId(userId);
        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, passengers);
    }
}
