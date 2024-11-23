package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.flight.FlightDtoConverter;
import com.walking.tbooking.converter.dto.flight.request.CreateFlightRequestConverter;
import com.walking.tbooking.converter.dto.flight.request.UpdateFlightRequestConverter;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.filter.RequestJsonDeserializerFilter;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.model.flight.FlightDto;
import com.walking.tbooking.model.flight.request.CreateFlightRequest;
import com.walking.tbooking.model.flight.request.UpdateFlightRequest;
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
    private CreateFlightRequestConverter createFlightRequestConverter;
    private UpdateFlightRequestConverter updateFlightRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.flightService = (FlightService) servletContext.getAttribute(ContextAttributeNames.FLIGHT_SERVICE);

        this.flightDtoConverter = (FlightDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.FLIGHT_DTO_CONVERTER);
        this.createFlightRequestConverter = (CreateFlightRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_FLIGHT_REQUEST_CONVERTER);
        this.updateFlightRequestConverter = (UpdateFlightRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.UPDATE_FLIGHT_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var idParam = req.getParameter("id");

        if (idParam != null) {
            var id = Long.valueOf(idParam);
            var flight = flightService.getFlight(id);
            var flightDto = flightDtoConverter.convert(flight);

            req.setAttribute("flight", flightDto);
        } else {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (User) req.getSession().getAttribute("user");

        if (user.getRole().equals(Role.USER)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter()
                    .write("Доступ запрешен");
            return;
        }

        var createFlightRequest = (CreateFlightRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var flight = createFlightRequestConverter.convert(createFlightRequest);
        var createdFlight = flightService.create(flight);
        var flightDto = flightDtoConverter.convert(createdFlight);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, flightDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (User) req.getSession().getAttribute("user");

        if (user.getRole().equals(Role.USER)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter()
                    .write("Доступ запрешен");
            return;
        }

        var updatedFlightRequest = (UpdateFlightRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var flight = updateFlightRequestConverter.convert(updatedFlightRequest);
        var updatedFlight = flightService.update(flight);
        var flightDto = flightDtoConverter.convert(updatedFlight);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, flightDto);
    }
}
