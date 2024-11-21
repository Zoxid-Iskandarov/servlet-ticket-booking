package com.walking.tbooking.model.user.request;

import com.walking.tbooking.domain.users.Role;

public class CreateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;

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
