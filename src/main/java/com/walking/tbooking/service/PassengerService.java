package com.walking.tbooking.service;

import com.walking.tbooking.domain.passenger.Passenger;
import com.walking.tbooking.repository.PassengerRepository;

import java.util.List;

public class PassengerService {
    private PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    public List<Passenger> getPassengersByUserId(Long userId) {
        return passengerRepository.findByUserId(userId);
    }

    public Passenger create(Passenger passenger) {
        return passengerRepository.create(passenger);
    }

    public Passenger update(Passenger passenger) {
        return passengerRepository.update(passenger);
    }

    public boolean delete(Long id) {
        return passengerRepository.deleteById(id);
    }
}
