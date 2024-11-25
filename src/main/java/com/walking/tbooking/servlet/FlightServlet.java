package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.flight.FlightDtoConverter;
import com.walking.tbooking.model.flight.FlightDto;
import com.walking.tbooking.service.FlightService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class FlightServlet extends HttpServlet {
    private FlightService flightService;

    private FlightDtoConverter flightDtoConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.flightService = (FlightService) servletContext.getAttribute(ContextAttributeNames.FLIGHT_SERVICE);

        this.flightDtoConverter = (FlightDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.FLIGHT_DTO_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var flightId = Long.parseLong(req.getParameter("id"));
            var flight = flightService.getFlight(flightId);
            var flightDto = flightDtoConverter.convert(flight);

            req.setAttribute("flight", flightDto);
        } catch (NumberFormatException e) {
            var flights = flightService.getFlights();
            var responseFlight = new ArrayList<FlightDto>();

            for (var flight : flights) {
                responseFlight.add(flightDtoConverter.convert(flight));
            }

            req.setAttribute("flights", responseFlight);
        }

        req.getRequestDispatcher("/flight.jsp")
                .forward(req, resp);
    }
}
