package com.walking.tbooking.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.tbooking.constant.ContextAttributeNames;
import com.walking.tbooking.converter.db.*;
import com.walking.tbooking.converter.dto.airport.AirportDtoConverter;
import com.walking.tbooking.converter.dto.airport.request.CreateAirportRequestConverter;
import com.walking.tbooking.converter.dto.airport.request.UpdateAirportRequestConverter;
import com.walking.tbooking.converter.dto.flight.FlightDtoConverter;
import com.walking.tbooking.converter.dto.flight.request.CreateFlightRequestConverter;
import com.walking.tbooking.converter.dto.flight.request.UpdateFlightRequestConverter;
import com.walking.tbooking.converter.dto.passenger.PassengerDtoConverter;
import com.walking.tbooking.converter.dto.passenger.request.CreatePassengerRequestConverter;
import com.walking.tbooking.converter.dto.passenger.request.UpdatePassengerRequestConverter;
import com.walking.tbooking.converter.dto.ticket.TicketDtoConverter;
import com.walking.tbooking.converter.dto.ticket.request.CreateTicketRequestConverter;
import com.walking.tbooking.converter.dto.user.UserDtoConverter;
import com.walking.tbooking.converter.dto.user.request.CreateAdminRequestConverter;
import com.walking.tbooking.converter.dto.user.request.UpdateAdminRequestConverter;
import com.walking.tbooking.converter.dto.user.request.UpdateUserRequestConverter;
import com.walking.tbooking.repository.*;
import com.walking.tbooking.service.*;
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

        var airportConverter = new AirportConverter();
        servletContext.setAttribute(ContextAttributeNames.AIRPORT_CONVERTER, airportConverter);

        var flightConverter = new FlightConverter();
        servletContext.setAttribute(ContextAttributeNames.FLIGHT_CONVERTER, flightConverter);

        var ticketConverter = new TicketConverter();
        servletContext.setAttribute(ContextAttributeNames.TICKET_CONVERTER, ticketConverter);

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

        var airportDtoConverter = new AirportDtoConverter();
        servletContext.setAttribute(ContextAttributeNames.AIRPORT_DTO_CONVERTER, airportDtoConverter);

        var createAirportRequestConverter = new CreateAirportRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_AIRPORT_REQUEST_CONVERTER, createAirportRequestConverter);

        var updateAirportRequestConverter = new UpdateAirportRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_AIRPORT_REQUEST_CONVERTER, updateAirportRequestConverter);

        var flightDtoConverter = new FlightDtoConverter();
        servletContext.setAttribute(ContextAttributeNames.FLIGHT_DTO_CONVERTER, flightDtoConverter);

        var createFlightRequestConverter = new CreateFlightRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_FLIGHT_REQUEST_CONVERTER, createFlightRequestConverter);

        var updateFlightRequestConverter = new UpdateFlightRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_FLIGHT_REQUEST_CONVERTER, updateFlightRequestConverter);

        var ticketDtoConverter = new TicketDtoConverter();
        servletContext.setAttribute(ContextAttributeNames.TICKET_DTO_CONVERTER, ticketDtoConverter);

        var createTicketRequestConverter = new CreateTicketRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_TICKET_REQUEST_CONVERTER, createTicketRequestConverter);

        var userRepository = new UserRepository(dataSource, userConverter);
        servletContext.setAttribute(ContextAttributeNames.USER_REPOSITORY, userRepository);

        var passengerRepository = new PassengerRepository(dataSource, passengerConverter);
        servletContext.setAttribute(ContextAttributeNames.PASSENGER_REPOSITORY, passengerRepository);

        var airportRepository = new AirportRepository(dataSource, airportConverter);
        servletContext.setAttribute(ContextAttributeNames.AIRPORT_REPOSITORY, airportRepository);

        var flightRepository = new FlightRepository(dataSource, flightConverter);
        servletContext.setAttribute(ContextAttributeNames.FLIGHT_REPOSITORY, flightRepository);

        var ticketRepository = new TicketRepository(dataSource, ticketConverter);
        servletContext.setAttribute(ContextAttributeNames.TICKET_REPOSITORY, ticketRepository);

        var encodingService = new EncodingService();
        servletContext.setAttribute(ContextAttributeNames.ENCODING_SERVICE, encodingService);

        var userService = new UserService(encodingService, userRepository);
        servletContext.setAttribute(ContextAttributeNames.USER_SERVICE, userService);

        var passengerService = new PassengerService(passengerRepository);
        servletContext.setAttribute(ContextAttributeNames.PASSENGER_SERVICE, passengerService);

        var airportService = new AirportService(airportRepository);
        servletContext.setAttribute(ContextAttributeNames.AIRPORT_SERVICE, airportService);

        var flightService = new FlightService(flightRepository);
        servletContext.setAttribute(ContextAttributeNames.FLIGHT_SERVICE, flightService);

        var ticketService = new TicketService(ticketRepository);
        servletContext.setAttribute(ContextAttributeNames.TICKET_SERVICE, ticketService);

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
