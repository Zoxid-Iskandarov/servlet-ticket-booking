package com.walking.tbooking.model.user.request;

public class UpdateUserRequest {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
}
