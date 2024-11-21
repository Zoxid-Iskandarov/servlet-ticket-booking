package com.walking.tbooking.converter.dto.user;

import com.walking.tbooking.converter.Converter;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.model.user.UserDto;

public class UserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User from) {
        var userDto = new UserDto();

        userDto.setEmail(from.getEmail());
        userDto.setFirstName(from.getFirstName());
        userDto.setLastName(from.getLastName());
        userDto.setPatronymic(from.getPatronymic());
        userDto.setRole(from.getRole());

        return userDto;
    }
}
