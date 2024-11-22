package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.dto.user.UserDtoConverter;
import com.walking.tbooking.converter.dto.user.request.UpdateUserRequestConverter;
import com.walking.tbooking.filter.RequestJsonDeserializerFilter;
import com.walking.tbooking.filter.ResponseJsonSerializerFilter;
import com.walking.tbooking.model.user.request.UpdateUserRequest;
import com.walking.tbooking.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserServlet extends HttpServlet {
    private UserService userService;

    private UserDtoConverter userDtoConverter;
    private UpdateUserRequestConverter updateUserRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);

        this.userDtoConverter = (UserDtoConverter) servletContext.getAttribute(
                ContextAttributeNames.USER_DTO_CONVERTER);
        this.updateUserRequestConverter = (UpdateUserRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.UPDATE_USER_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userRequest = (UpdateUserRequest) req.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var user = updateUserRequestConverter.convert(userRequest);
        var updatedUser = userService.update(user);
        var userDto = userDtoConverter.convert(updatedUser);

        req.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, userDto);
    }
}
