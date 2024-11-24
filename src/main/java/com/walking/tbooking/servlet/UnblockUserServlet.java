package com.walking.tbooking.servlet;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UnblockUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute(
                ContextAttributeNames.USER_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Long.parseLong(req.getParameter("id"));
        var flag = userService.unblockUser(id);

        if (flag) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter()
                    .write("Пользователь успешно разблокирован");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter()
                    .write("Произошла ошибка при разблокировке пользователя");
        }
    }
}