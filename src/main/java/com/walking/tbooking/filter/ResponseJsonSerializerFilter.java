package com.walking.tbooking.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.walking.tbooking.constant.ContextAttributeNames;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ResponseJsonSerializerFilter extends HttpFilter {
    public static final String POJO_RESPONSE_BODY = "pojoResponseBody";

    private static final Logger log = LogManager.getLogger(ResponseJsonSerializerFilter.class);

    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.objectMapper = (ObjectMapper) servletContext.getAttribute(ContextAttributeNames.OBJECT_MAPPER);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);

        var pojoBody = req.getAttribute(POJO_RESPONSE_BODY);

        if (pojoBody == null) {
            return;
        }

        try {
            res.getOutputStream()
                    .write(objectMapper.writeValueAsBytes(pojoBody));

            res.setContentType("application/json");
        } catch (IOException e) {
            log.error("Ошибка формирования тела ответа", e);
            throw e;
        }
    }
}
