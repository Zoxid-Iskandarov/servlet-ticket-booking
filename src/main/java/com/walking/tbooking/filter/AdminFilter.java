package com.walking.tbooking.filter;

import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class AdminFilter extends HttpFilter {
    private static final Set<String> ADMIN_API = Set.of(
            "/admin", "/admin/passenger", "/admin/ticket", "/admin/flight", "/admin/airport", "/user/block", "/user/unblock"
    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!ADMIN_API.contains(req.getServletPath())) {
            chain.doFilter(req, res);
            return;
        }

        var user = (User) req.getSession().getAttribute("user");

        if (user.getRole().equals(Role.USER)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter()
                    .write("Доступ запрещен");
            return;
        }

        chain.doFilter(req, res);
    }
}
