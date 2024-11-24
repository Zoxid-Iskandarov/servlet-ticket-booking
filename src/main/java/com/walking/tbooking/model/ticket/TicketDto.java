package com.walking.tbooking.model.ticket;

import com.walking.tbooking.domain.ticket.ServiceClass;

public class TicketDto {
    private Long flightId;
    private Long passengerId;
    private String seatNumber;
    private ServiceClass serviceClass;
    private Integer baggageAllowance;
    private Integer handBaggageAllowance;
    private boolean isCanceled;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public Integer getBaggageAllowance() {
        return baggageAllowance;
    }

    public void setBaggageAllowance(Integer baggageAllowance) {
        this.baggageAllowance = baggageAllowance;
    }

    public Integer getHandBaggageAllowance() {
        return handBaggageAllowance;
    }

    public void setHandBaggageAllowance(Integer handBaggageAllowance) {
        this.handBaggageAllowance = handBaggageAllowance;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
