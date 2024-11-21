package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.exception.AuthException;
import com.walking.tbooking.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");

        try {
            var user = userService.auth(email, password);
            userService.updateLastLogin(user.getId());

            var session = req.getSession(true);

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());

            if (user.getRole().equals(Role.ADMIN)) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/passenger");
            }
        } catch (AuthException e) {
            handleError(req, resp, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/auth");
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, AuthException e) throws ServletException, IOException {
        resp.setStatus(401);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
