package com.walking.tbooking.model.passenger.request;

import com.walking.tbooking.domain.passenger.Gender;

import java.time.LocalDate;

public class UpdatePassengerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Gender gender;
    private LocalDate birthDate;
    private String passportData;
    private Long userId;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPassportData() {
        return passportData;
    }

    public Long getUserId() {
        return userId;
    }
}
