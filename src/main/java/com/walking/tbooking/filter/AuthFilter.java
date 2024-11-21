package com.walking.tbooking.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class AuthFilter extends HttpFilter {
    private static final Set<String> UNSECURED_API = Set.of(
            "/signUp", "/auth"
    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (UNSECURED_API.contains(req.getServletPath())) {
            chain.doFilter(req, res);
            return;
        }

        var session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        chain.doFilter(req, res);
    }
}
