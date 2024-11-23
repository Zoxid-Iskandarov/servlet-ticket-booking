package com.walking.tbooking.model.airport.request;

public class UpdateAirportRequest {
    private Integer id;
    private String code;
    private String name;
    private String address;

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
