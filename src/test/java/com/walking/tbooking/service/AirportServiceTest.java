package com.walking.tbooking.service;

import com.walking.tbooking.domain.airport.Airport;
import com.walking.tbooking.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    @Test
    void getAllAirports_value_success() {
        List<Airport> airports = List.of(mock(Airport.class));
        doReturn(airports).when(airportRepository).findAll();

        var actual = airportService.getAirports();

        assertSame(airports, actual);
        assertFalse(actual.isEmpty());

        verify(airportRepository).findAll();
    }

    @Test
    void getAirports_empty_success() {
        List<Airport> airports = List.of();
        doReturn(airports).when(airportRepository).findAll();

        var actual = airportService.getAirports();

        assertSame(airports, actual);
        assertTrue(actual.isEmpty());

        verify(airportRepository).findAll();
    }

    @Test
    void getAirports_repositoryError_failed() {
        doThrow(RuntimeException.class).when(airportRepository).findAll();

        Executable executable = () -> airportService.getAirports();

        assertThrows(RuntimeException.class, executable);

        verify(airportRepository).findAll();
    }

    @Test
    void getAirport_value_success() {
        Optional<Airport> airport = Optional.of(mock(Airport.class));
        doReturn(airport).when(airportRepository).findById(1);

        var actual = airportService.getAirport(1);

        assertNotNull(actual);
        assertSame(airport.get(), actual);

        verify(airportRepository).findById(1);
    }

    @Test
    void getAirport_empty_failed() {
        doReturn(Optional.empty()).when(airportRepository).findById(1);

        Executable executable = () -> airportService.getAirport(1);

        assertThrows(IllegalArgumentException.class, executable);

        verify(airportRepository).findById(1);
    }

    @Test
    void getAirport_repositoryError_failed() {
        doThrow(RuntimeException.class).when(airportRepository).findById(1);

        Executable executable = () -> airportService.getAirport(1);

        assertThrows(RuntimeException.class, executable);

        verify(airportRepository).findById(1);
    }

    @Test
    void create_success() {
        var airport = getAirport();

        doReturn(airport).when(airportRepository).create(airport);
        var actual = airportService.create(airport);

        assertSame(airport, actual);
        assertEquals(airport.getId(), actual.getId());

        verify(airportRepository).create(airport);
    }

    @Test
    void create_repositoryError_failed() {
        doThrow(RuntimeException.class).when(airportRepository).create(getAirport());

        Executable executable = () -> airportService.create(getAirport());

        assertThrows(RuntimeException.class, executable);

        verify(airportRepository).create(any());
    }

    @Test
    void update_success() {
        var airport = getAirport(1);
        doReturn(airport).when(airportRepository).update(airport);

        var actual = airportService.update(airport);

        assertSame(airport, actual);
        assertEquals(airport.getId(), actual.getId());

        verify(airportRepository).update(airport);
    }

    @Test
    void update_repositoryError_failed() {
        doThrow(RuntimeException.class).when(airportRepository).update(getAirport());

        Executable executable = () -> airportService.update(getAirport(1));

        assertThrows(RuntimeException.class, executable);

        verify(airportRepository).update(any());
    }

    @Test
    void delete_success() {
        doReturn(true).when(airportRepository).deleteById(1);

        var actual = airportService.delete(1);

        assertTrue(actual);

        verify(airportRepository).deleteById(1);
    }

    @Test
    void delete_failed() {
        doReturn(false).when(airportRepository).deleteById(1);

        var actual = airportService.delete(1);

        assertFalse(actual);

        verify(airportRepository).deleteById(1);
    }

    @Test
    void delete_repositoryError_failed() {
        doThrow(RuntimeException.class).when(airportRepository).deleteById(1);

        Executable executable = () -> airportService.delete(1);

        assertThrows(RuntimeException.class, executable);

        verify(airportRepository).deleteById(1);
    }

    private Airport getAirport() {
        return getAirport(null);
    }

    private Airport getAirport(Integer id) {
        var airport = new Airport();

        airport.setId(id);
        airport.setCode("TestCode");
        airport.setName("TestName");
        airport.setAddress("TestAddress");

        return airport;
    }
}