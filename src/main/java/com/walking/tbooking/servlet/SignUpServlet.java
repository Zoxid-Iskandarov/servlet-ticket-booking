package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.exception.DuplicateUserException;
import com.walking.tbooking.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = new User();

        user.setEmail(req.getParameter("email"));
        user.setPasswordHash(req.getParameter("password"));
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setPatronymic(req.getParameter("patronymic"));
        user.setRole(Role.USER);

        try {
            userService.create(user);

            resp.sendRedirect(req.getContextPath() + "/auth");
        } catch (DuplicateUserException e) {
            handleError(req, resp, e);
        }
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, DuplicateUserException e) throws ServletException, IOException {
        resp.setStatus(409);
        req.getRequestDispatcher("/signUp").forward(req, resp);
    }
}
