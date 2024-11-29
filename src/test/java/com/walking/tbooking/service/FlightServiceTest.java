package com.walking.tbooking.service;

import com.walking.tbooking.domain.flight.Flight;
import com.walking.tbooking.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void getFlights_value_success() {
        List<Flight> flights = List.of(mock(Flight.class));
        doReturn(flights).when(flightRepository).findAll();

        var actual = flightService.getFlights();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(flights.size(), actual.size());
        assertSame(flights, actual);

        verify(flightRepository).findAll();
    }

    @Test
    void getFlights_empty_success() {
        List<Flight> flights = List.of();
        doReturn(flights).when(flightRepository).findAll();

        var actual = flightService.getFlights();

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        assertSame(flights, actual);

        verify(flightRepository).findAll();
    }

    @Test
    void getFlights_repositoryError_failed() {
        doThrow(RuntimeException.class).when(flightRepository).findAll();

        Executable executable = () -> flightService.getFlights();

        assertThrows(RuntimeException.class, executable);

        verify(flightRepository).findAll();
    }

    @Test
    void getFlight_value_success() {
        Optional<Flight> flight = Optional.of(mock(Flight.class));
        doReturn(flight).when(flightRepository).findById(1L);

        var actual = flightService.getFlight(1L);

        assertNotNull(actual);
        assertSame(flight.get(), actual);

        verify(flightRepository).findById(1L);
    }

    @Test
    void getFlight_notFound_exception_failed() {
        doReturn(Optional.empty()).when(flightRepository).findById(1L);

        Executable executable = () -> flightService.getFlight(1L);

        assertThrows(RuntimeException.class, executable);

        verify(flightRepository).findById(1L);
    }

    @Test
    void getFlight_repositoryError_failed() {
        doThrow(RuntimeException.class).when(flightRepository).findById(1L);

        Executable executable = () -> flightService.getFlight(1L);

        assertThrows(RuntimeException.class, executable);

        verify(flightRepository).findById(1L);
    }

    @Test
    void create_success() {
        var flight = getFlight();
        doReturn(flight).when(flightRepository).create(flight);

        var actual = flightService.create(flight);

        assertSame(flight, actual);
        assertEquals(flight.getId(), actual.getId());

        verify(flightRepository).create(flight);
    }

    @Test
    void create_repositoryError_failed() {
        doThrow(RuntimeException.class).when(flightRepository).create(getFlight());

        Executable executable = () -> flightService.create(getFlight());

        assertThrows(RuntimeException.class, executable);

        verify(flightRepository).create(any(Flight.class));
    }

    @Test
    void update_success() {
        var flight = getFlight(1L);
        doReturn(flight).when(flightRepository).update(flight);

        var actual = flightService.update(flight);

        assertSame(flight, actual);
        assertEquals(flight.getId(), actual.getId());

        verify(flightRepository).update(flight);
    }

    @Test
    void update_repositoryError_failed() {
        var flight = getFlight(1L);
        doThrow(RuntimeException.class).when(flightRepository).update(flight);

        Executable executable = () -> flightService.update(flight);

        assertThrows(RuntimeException.class, executable);

        verify(flightRepository).update(flight);
    }

    @Test
    void delete_success() {
        doReturn(true).when(flightRepository).deleteById(1L);
        var actual = flightService.delete(1L);

        assertTrue(actual);

        verify(flightRepository).deleteById(1L);
    }

    @Test
    void delete_failed() {
        doReturn(false).when(flightRepository).deleteById(1L);
        var actual = flightService.delete(1L);

        assertFalse(actual);

        verify(flightRepository).deleteById(1L);
    }

    @Test
    void delete_repositoryError_failed() {
        doThrow(RuntimeException.class).when(flightRepository).deleteById(1L);

        Executable executable = () -> flightService.delete(1L);

        assertThrows(RuntimeException.class, executable);
    }

    private Flight getFlight() {
        return getFlight(null);
    }

    private Flight getFlight(Long id) {
        var flight = new Flight();

        flight.setId(id);
        flight.setDepartureDate(LocalDateTime.now());
        flight.setArrivalDate(LocalDateTime.now());
        flight.setDepartureAirportId(1);
        flight.setArrivalAirportId(2);
        flight.setTotalSeats(120);
        flight.setAvailableSeats(150);

        return flight;
    }
}