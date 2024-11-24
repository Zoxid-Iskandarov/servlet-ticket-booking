package com.walking.tbooking.listener;

import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.repository.UserRepository;
import com.walking.tbooking.service.EncodingService;
import com.walking.tbooking.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InitialAdminSetupListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(InitialAdminSetupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Запушено инициальзация первого аднимистратора");

        var servletContext = event.getServletContext();

        var userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);

        var encodingService = (EncodingService) servletContext.getAttribute(ContextAttributeNames.ENCODING_SERVICE);

        if (!userService.adminPresent()) {
            var user = new User();

            user.setEmail("admin@default.com");
            user.setPasswordHash(encodingService.encode("first_admin"));
            user.setFirstName("Ivan");
            user.setLastName("Ivanov");
            user.setPatronymic("Ivanovich");
            user.setRole(Role.ADMIN);

            userService.create(user);
        }

        log.info("Завершено инициальзация первого аднимистратора");
    }
}
