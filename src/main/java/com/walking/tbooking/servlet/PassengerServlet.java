package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.passenger.PassengerDtoConverter;
import com.walking.tbooking.converter.dto.passenger.request.CreatePassengerRequestConverter;
import com.walking.tbooking.converter.dto.passenger.request.UpdatePassengerRequestConverter;
import com.walking.tbooking.filter.RequestJsonDeserializerFilter;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.model.passenger.request.CreatePassengerRequest;
import com.walking.tbooking.model.passenger.request.UpdatePassengerRequest;
import com.walking.tbooking.service.PassengerService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PassengerServlet extends HttpServlet {
    private PassengerService passengerService;

    private PassengerDtoConverter passengerDtoConverter;
    private CreatePassengerRequestConverter createPassengerRequestConverter;
    private UpdatePassengerRequestConverter updatePassengerRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.passengerService = (PassengerService) servletContext.getAttribute(ContextAttributeNames.PASSENGER_SERVICE);

        this.passengerDtoConverter = (PassengerDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.PASSENGER_DTO_CONVERTER);
        this.createPassengerRequestConverter = (CreatePassengerRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_PASSENGER_REQUEST_CONVERTER);
        this.updatePassengerRequestConverter = (UpdatePassengerRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.UPDATE_PASSENGER_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = (Long) req.getSession().getAttribute("userId");

        req.setAttribute("passengersByUserId", passengerService.getPassengersByUserId(userId));

        req.getRequestDispatcher("passengers.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var passengerRequest = (CreatePassengerRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var passenger = createPassengerRequestConverter.convert(passengerRequest);
        var createdPassenger = passengerService.create(passenger);
        var passengerDto = passengerDtoConverter.convert(createdPassenger);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, passengerDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var passengerRequest = (UpdatePassengerRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var passenger = updatePassengerRequestConverter.convert(passengerRequest);
        var updatedPassenger = passengerService.update(passenger);
        var passengerDto = passengerDtoConverter.convert(updatedPassenger);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, passengerDto);
    }
}
