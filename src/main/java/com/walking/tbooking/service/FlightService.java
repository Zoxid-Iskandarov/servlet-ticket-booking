package com.walking.tbooking.service;

import com.walking.tbooking.domain.flight.Flight;
import com.walking.tbooking.repository.FlightRepository;

import java.util.List;

public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlight(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Рейс не найден"));
    }

    public Flight create(Flight flight) {
        return flightRepository.create(flight);
    }

    public Flight update(Flight flight) {
        return flightRepository.update(flight);
    }

    public boolean delete(Long id) {
        return flightRepository.deleteById(id);
    }
}
