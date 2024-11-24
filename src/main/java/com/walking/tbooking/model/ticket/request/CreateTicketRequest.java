package com.walking.tbooking.model.ticket.request;

import com.walking.tbooking.domain.ticket.ServiceClass;

public class CreateTicketRequest {
    private Long flightId;
    private Long passengerId;
    private String seatNumber;
    private ServiceClass serviceClass;
    private Integer baggageAllowance;
    private Integer handBaggageAllowance;

    public Long getFlightId() {
        return flightId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public Integer getBaggageAllowance() {
        return baggageAllowance;
    }

    public Integer getHandBaggageAllowance() {
        return handBaggageAllowance;
    }
}
