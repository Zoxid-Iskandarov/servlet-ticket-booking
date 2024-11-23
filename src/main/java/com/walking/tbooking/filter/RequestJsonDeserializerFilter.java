package com.walking.tbooking.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.model.airport.request.CreateAirportRequest;
import com.walking.tbooking.model.airport.request.UpdateAirportRequest;
import com.walking.tbooking.model.flight.request.CreateFlightRequest;
import com.walking.tbooking.model.flight.request.UpdateFlightRequest;
import com.walking.tbooking.model.passenger.request.CreatePassengerRequest;
import com.walking.tbooking.model.passenger.request.UpdatePassengerRequest;
import com.walking.tbooking.model.user.request.CreateUserRequest;
import com.walking.tbooking.model.user.request.UpdateUserRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestJsonDeserializerFilter extends HttpFilter {
    public static final String POJO_REQUEST_BODY = "pojoRequestBody";

    private static final Logger log = LogManager.getLogger(RequestJsonDeserializerFilter.class);

    private Map<String, TypeReference<?>> targetTypes;
    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.objectMapper = (ObjectMapper) config.getServletContext().getAttribute(ContextAttributeNames.OBJECT_MAPPER);

        this.objectMapper.registerModule(new JavaTimeModule());

        initTargetTypes();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!"application/json".equals(req.getContentType()) || req.getContentLength() == 0) {
            chain.doFilter(req, res);
            return;
        }

        byte[] jsonBody = req.getInputStream().readAllBytes();

        try {
            var targetType = getTargetType(req);
            Object pojoBody = objectMapper.readValue(jsonBody, targetType);

            req.setAttribute(POJO_REQUEST_BODY, pojoBody);
        } catch (IOException e) {
            log.error("Ошибка десериализации тела запроса", e);
            throw e;
        }

        chain.doFilter(req, res);
    }

    private TypeReference<?> getTargetType(HttpServletRequest request) {
        var key = "%s&&%s".formatted(request.getServletPath(), request.getMethod());

        return targetTypes.getOrDefault(key, new TypeReference<>() {
        });
    }

    private void initTargetTypes() {
        targetTypes = new ConcurrentHashMap<>();

        targetTypes.put("/admin&&POST", new TypeReference<CreateUserRequest>() {
        });
        targetTypes.put("/admin&&PUT", new TypeReference<UpdateUserRequest>() {
        });
        targetTypes.put("/user&&PUT", new TypeReference<UpdateUserRequest>() {
        });
        targetTypes.put("/passenger&&POST", new TypeReference<CreatePassengerRequest>() {
        });
        targetTypes.put("/passenger&&PUT", new TypeReference<UpdatePassengerRequest>() {
        });
        targetTypes.put("/airport&&POST", new TypeReference<CreateAirportRequest>() {
        });
        targetTypes.put("/airport&&PUT", new TypeReference<UpdateAirportRequest>() {
        });
        targetTypes.put("/flight&&POST", new TypeReference<CreateFlightRequest>() {
        });
        targetTypes.put("/flight&&PUT", new TypeReference<UpdateFlightRequest>() {
        });
    }
}
