package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.airport.AirportDtoConverter;
import com.walking.tbooking.model.airport.AirportDto;
import com.walking.tbooking.service.AirportService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class AirportServlet extends HttpServlet {
    private AirportService airportService;

    private AirportDtoConverter airportDtoConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.airportService = (AirportService) servletContext.getAttribute(ContextAttributeNames.AIRPORT_SERVICE);

        this.airportDtoConverter = (AirportDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.AIRPORT_DTO_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var airportId = Integer.parseInt(req.getParameter("id"));
            var airport = airportService.getAirport(airportId);
            var airportDto = airportDtoConverter.convert(airport);

            req.setAttribute("airport", airportDto);
        } catch (NumberFormatException e) {
            var airports = airportService.getAirports();
            var response = new ArrayList<AirportDto>();

            for (var airport : airports) {
                response.add(airportDtoConverter.convert(airport));
            }

            req.setAttribute("airports", response);
        }

        req.getRequestDispatcher("airport.jsp")
                .forward(req, resp);
    }
}
