package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.airport.AirportDtoConverter;
import com.walking.tbooking.converter.dto.airport.request.CreateAirportRequestConverter;
import com.walking.tbooking.converter.dto.airport.request.UpdateAirportRequestConverter;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.filter.RequestJsonDeserializerFilter;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.model.airport.AirportDto;
import com.walking.tbooking.model.airport.request.CreateAirportRequest;
import com.walking.tbooking.model.airport.request.UpdateAirportRequest;
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
    private CreateAirportRequestConverter createAirportRequestConverter;
    private UpdateAirportRequestConverter updateAirportRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.airportService = (AirportService) servletContext.getAttribute(ContextAttributeNames.AIRPORT_SERVICE);

        this.airportDtoConverter = (AirportDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.AIRPORT_DTO_CONVERTER);
        this.createAirportRequestConverter = (CreateAirportRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_AIRPORT_REQUEST_CONVERTER);
        this.updateAirportRequestConverter = (UpdateAirportRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.UPDATE_AIRPORT_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var paramId = req.getParameter("id");

        if (paramId != null) {
            var id = Integer.valueOf(req.getParameter("id"));
            var airport = airportService.getAirport(id);
            var airportDto = airportDtoConverter.convert(airport);

            req.setAttribute("airport", airportDto);
        } else {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (User) req.getSession().getAttribute("user");

        if (user.getRole().equals(Role.USER)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter()
                    .write("Доступ запрешен");
            return;
        }

        var createAirportRequest = (CreateAirportRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var airport = createAirportRequestConverter.convert(createAirportRequest);
        var createdAirport = airportService.create(airport);
        var airportDto = airportDtoConverter.convert(createdAirport);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, airportDto);
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

        var updateAirportRequest = (UpdateAirportRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var airport = updateAirportRequestConverter.convert(updateAirportRequest);
        var updatedAirport = airportService.update(airport);
        var airportDto = airportDtoConverter.convert(updatedAirport);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, airportDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (User) req.getSession().getAttribute("user");

        if (user.getRole().equals(Role.USER)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter()
                    .write("Доступ запрешен");
            return;
        }

        var id = Integer.valueOf(req.getParameter("id"));
        var isDelete = airportService.delete(id);

        if (isDelete) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter()
                    .write("Аэропорт успешно удален");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter()
                    .write("Аэропорт не найден");
        }
    }
}
