package com.walking.tbooking.filter;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserStatusFilter extends HttpFilter {
    private UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute(
                ContextAttributeNames.USER_SERVICE);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var userId = (Long) req.getSession().getAttribute("userId");

        if (userId == null) {
            chain.doFilter(req, res);
            return;
        }

        if (userService.getUserStatus(userId)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter()
                    .write("Доступ запрещен");
        } else {
            chain.doFilter(req, res);
        }
    }
}
