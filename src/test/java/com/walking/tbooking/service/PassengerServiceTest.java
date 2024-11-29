package com.walking.tbooking.service;

import com.walking.tbooking.domain.passenger.Gender;
import com.walking.tbooking.domain.passenger.Passenger;
import com.walking.tbooking.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void getPassengers_value_success() {
        List<Passenger> passengers = List.of(mock(Passenger.class));
        doReturn(passengers).when(passengerRepository).findAll();

        var actual = passengerService.getPassengers();

        assertFalse(actual.isEmpty());
        assertSame(passengers, actual);

        verify(passengerRepository).findAll();
    }

    @Test
    void getPassengers_empty_success() {
        List<Passenger> passengers = List.of();
        doReturn(passengers).when(passengerRepository).findAll();

        var actual = passengerService.getPassengers();

        assertTrue(actual.isEmpty());
        assertSame(passengers, actual);

        verify(passengerRepository).findAll();
    }

    @Test
    void getPassengers_repositoryError_failed() {
        doThrow(RuntimeException.class).when(passengerRepository).findAll();

        Executable executable = () -> passengerService.getPassengers();

        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository).findAll();
    }

    @Test
    void getPassengersByUserId_value_success() {
        List<Passenger> passengers = List.of(mock(Passenger.class));
        doReturn(passengers).when(passengerRepository).findByUserId(1L);

        var actual = passengerService.getPassengersByUserId(1L);

        assertFalse(actual.isEmpty());
        assertSame(passengers, actual);

        verify(passengerRepository).findByUserId(1L);
    }

    @Test
    void getPassengersByUserId_empty_success() {
        List<Passenger> passengers = List.of();
        doReturn(passengers).when(passengerRepository).findByUserId(1L);

        var actual = passengerService.getPassengersByUserId(1L);

        assertTrue(actual.isEmpty());
        assertSame(passengers, actual);

        verify(passengerRepository).findByUserId(1L);
    }

    @Test
    void getPassengersByUserId_repositoryError_failed() {
        doThrow(RuntimeException.class).when(passengerRepository).findByUserId(1L);

        Executable executable = () -> passengerService.getPassengersByUserId(1L);

        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository).findByUserId(1L);
    }

    @Test
    void getPassengerById_value_success() {
        Optional<Passenger> passenger = Optional.of(mock(Passenger.class));
        doReturn(passenger).when(passengerRepository).findById(1L);

        var actual = passengerService.getPassengerById(1L);

        assertSame(passenger.get(), actual);
        assertEquals(passenger.get().getId(), actual.getId());

        verify(passengerRepository).findById(1L);
    }

    @Test
    void getPassengerById_notFound_failed() {
        doThrow(IllegalArgumentException.class).when(passengerRepository).findById(1L);

        Executable executable = () -> passengerService.getPassengerById(1L);

        assertThrows(IllegalArgumentException.class, executable);

        verify(passengerRepository).findById(1L);
    }

    @Test
    void getPassengerById_repositoryError_failed() {
        doThrow(RuntimeException.class).when(passengerRepository).findById(1L);

        Executable executable = () -> passengerService.getPassengerById(1L);

        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository).findById(1L);
    }

    @Test
    void create_success() {
        var passenger = getPassenger();

        doReturn(passenger).when(passengerRepository).create(passenger);

        var actual = passengerService.create(passenger);

        assertSame(passenger, actual);
        assertEquals(passenger.getId(), actual.getId());

        verify(passengerRepository).create(passenger);
    }

    @Test
    void create_repositoryError_failed() {
        doThrow(RuntimeException.class).when(passengerRepository).create(any());

        Executable executable = () -> passengerService.create(getPassenger());

        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository).create(any());
    }

    @Test
    void update_success() {
        var passenger = getPassenger(1L);

        doReturn(passenger).when(passengerRepository).update(passenger);

        var actual = passengerService.update(passenger);

        assertSame(passenger, actual);
        assertEquals(passenger.getId(), actual.getId());

        verify(passengerRepository).update(passenger);
    }

    @Test
    void update_repositoryError_failed() {
        doThrow(RuntimeException.class).when(passengerRepository).update(any());

        Executable executable = () -> passengerService.update(getPassenger());

        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository).update(any());
    }

    @Test
    void delete_success() {
        doReturn(true).when(passengerRepository).deleteById(1L, 1L);

        var actual = passengerService.delete(1L, 1L);

        assertTrue(actual);

        verify(passengerRepository).deleteById(1L, 1L);
    }

    @Test
    void delete_failed() {
        doReturn(false).when(passengerRepository).deleteById(1L, 1L);

        var actual = passengerService.delete(1L, 1L);

        assertFalse(actual);

        verify(passengerRepository).deleteById(1L, 1L);
    }

    @Test
    void delete_repositoryError_failed() {
        doThrow(RuntimeException.class).when(passengerRepository).deleteById(1L, 1L);

        Executable executable = () -> passengerService.delete(1L, 1L);

        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository).deleteById(1L, 1L);
    }

    private Passenger getPassenger() {
        return getPassenger(null);
    }

    private Passenger getPassenger(Long id) {
        var passenger = new Passenger();

        passenger.setId(id);
        passenger.setFirstName("test");
        passenger.setLastName("test");
        passenger.setPatronymic("test");
        passenger.setGender(Gender.MALE);
        passenger.setBirthDate(LocalDate.now());
        passenger.setPassportData("test");
        passenger.setUserId(1L);

        return passenger;
    }
}