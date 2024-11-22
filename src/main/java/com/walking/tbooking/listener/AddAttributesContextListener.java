package com.walking.tbooking.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.db.PassengerConverter;
import com.walking.tbooking.converter.db.UserConverter;
import com.walking.tbooking.converter.dto.passenger.PassengerDtoConverter;
import com.walking.tbooking.converter.dto.passenger.request.CreatePassengerRequestConverter;
import com.walking.tbooking.converter.dto.passenger.request.UpdatePassengerRequestConverter;
import com.walking.tbooking.converter.dto.user.UserDtoConverter;
import com.walking.tbooking.converter.dto.user.request.CreateAdminRequestConverter;
import com.walking.tbooking.converter.dto.user.request.UpdateAdminRequestConverter;
import com.walking.tbooking.converter.dto.user.request.UpdateUserRequestConverter;
import com.walking.tbooking.repository.PassengerRepository;
import com.walking.tbooking.repository.UserRepository;
import com.walking.tbooking.service.EncodingService;
import com.walking.tbooking.service.MigrationService;
import com.walking.tbooking.service.PassengerService;
import com.walking.tbooking.service.UserService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class AddAttributesContextListener implements ServletContextListener {
    private static final String HIKARI_PROPERTIES_PATH = "/WEB-INF/classes/hikari.properties";

    private static final Logger log = LogManager.getLogger(AddAttributesContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Запушена иницеализация атрибутов глобального контекста");

        var servletContext = event.getServletContext();

        var dataSource = hikariDataSource(servletContext);
        servletContext.setAttribute(ContextAttributeNames.DATASOURCE, dataSource);

        var userConverter = new UserConverter();
        servletContext.setAttribute(ContextAttributeNames.USER_CONVERTER, userConverter);

        var passengerConverter = new PassengerConverter();
        servletContext.setAttribute(ContextAttributeNames.PASSENGER_CONVERTER, passengerConverter);

        var userDtoConverter = new UserDtoConverter();
        servletContext.setAttribute(ContextAttributeNames.USER_DTO_CONVERTER, userDtoConverter);

        var updateUserRequestConverter = new UpdateUserRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_USER_REQUEST_CONVERTER, updateUserRequestConverter);

        var createAdminRequestConverter = new CreateAdminRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_ADMIN_REQUEST_CONVERTER, createAdminRequestConverter);

        var updateAdminRequestConverter = new UpdateAdminRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_ADMIN_REQUEST_CONVERTER, updateAdminRequestConverter);

        var passengerDtoConverter = new PassengerDtoConverter();
        servletContext.setAttribute(ContextAttributeNames.PASSENGER_DTO_CONVERTER, passengerDtoConverter);

        var createPassengerRequestConverter = new CreatePassengerRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_PASSENGER_REQUEST_CONVERTER, createPassengerRequestConverter);

        var updatePassengerRequestConverter = new UpdatePassengerRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_PASSENGER_REQUEST_CONVERTER, updatePassengerRequestConverter);

        var userRepository = new UserRepository(dataSource, userConverter);
        servletContext.setAttribute(ContextAttributeNames.USER_REPOSITORY, userRepository);

        var passengerRepository = new PassengerRepository(dataSource, passengerConverter);
        servletContext.setAttribute(ContextAttributeNames.PASSENGER_REPOSITORY, passengerRepository);

        var encodingService = new EncodingService();
        servletContext.setAttribute(ContextAttributeNames.ENCODING_SERVICE, encodingService);

        var userService = new UserService(encodingService, userRepository);
        servletContext.setAttribute(ContextAttributeNames.USER_SERVICE, userService);

        var passengerService = new PassengerService(passengerRepository);
        servletContext.setAttribute(ContextAttributeNames.PASSENGER_SERVICE, passengerService);

        var migrationService = new MigrationService(dataSource);
        servletContext.setAttribute(ContextAttributeNames.MIGRATION_SERVICE, migrationService);

        var objectMapper = new ObjectMapper();
        servletContext.setAttribute(ContextAttributeNames.OBJECT_MAPPER, objectMapper);

        log.info("Завершена инициализация атрибутов глобального контекста");
    }

    private DataSource hikariDataSource(ServletContext servletContext) {
        try (var propertiesInputStream = servletContext.getResourceAsStream(HIKARI_PROPERTIES_PATH)) {
            var hikariProperties = new Properties();
            hikariProperties.load(propertiesInputStream);

            var configuration = new HikariConfig(hikariProperties);

            return new HikariDataSource(configuration);
        } catch (IOException e) {
            log.error("Невозможно загрузить конфигурацию для HikariCP", e);

            throw new RuntimeException(e);
        }
    }
}
