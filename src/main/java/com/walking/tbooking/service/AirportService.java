package com.walking.tbooking.service;

import com.walking.tbooking.domain.airport.Airport;
import com.walking.tbooking.repository.AirportRepository;

import java.util.List;

public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAirports() {
        return airportRepository.findAll();
    }

    public Airport getAirport(Integer id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Аэропорт не найден"));
    }

    public Airport create(Airport airport) {
        return airportRepository.create(airport);
    }

    public Airport update(Airport airport) {
        return airportRepository.update(airport);
    }

    public boolean delete(Integer id) {
        return airportRepository.deleteById(id);
    }
}
