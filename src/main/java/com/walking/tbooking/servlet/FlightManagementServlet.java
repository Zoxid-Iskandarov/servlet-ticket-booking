package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.flight.FlightDtoConverter;
import com.walking.tbooking.converter.dto.flight.request.CreateFlightRequestConverter;
import com.walking.tbooking.converter.dto.flight.request.UpdateFlightRequestConverter;
import com.walking.tbooking.filter.RequestJsonDeserializerFilter;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.model.flight.request.CreateFlightRequest;
import com.walking.tbooking.model.flight.request.UpdateFlightRequest;
import com.walking.tbooking.service.FlightService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FlightManagementServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createFlightRequest = (CreateFlightRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var flight = createFlightRequestConverter.convert(createFlightRequest);
        var createdFlight = flightService.create(flight);
        var flightDto = flightDtoConverter.convert(createdFlight);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, flightDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var updatedFlightRequest = (UpdateFlightRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var flight = updateFlightRequestConverter.convert(updatedFlightRequest);
        var updatedFlight = flightService.update(flight);
        var flightDto = flightDtoConverter.convert(updatedFlight);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, flightDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var id = Long.parseLong(req.getParameter("id"));
            var idDelete = flightService.delete(id);

            if (idDelete) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter()
                        .write("Рейс успешно удален");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter()
                        .write("Рейс не найден");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID рейса");
        }
    }
}
