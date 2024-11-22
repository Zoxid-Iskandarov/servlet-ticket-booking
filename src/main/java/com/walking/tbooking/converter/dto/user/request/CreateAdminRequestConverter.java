package com.walking.tbooking.converter.dto.user.request;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.model.user.request.CreateUserRequest;

public class CreateAdminRequestConverter implements Converter<CreateUserRequest, User> {
    @Override
    public User convert(CreateUserRequest from) {
        var user = new User();

        user.setEmail(from.getEmail());
        user.setPasswordHash(from.getPassword());
        user.setFirstName(from.getFirstName());
        user.setLastName(from.getLastName());
        user.setPatronymic(from.getPatronymic());
        user.setRole(Role.ADMIN);

        return user;
    }
}
