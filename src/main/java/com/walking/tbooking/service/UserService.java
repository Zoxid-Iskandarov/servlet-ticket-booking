package com.walking.tbooking.service;

import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.exception.AuthException;
import com.walking.tbooking.exception.DuplicateUserException;
import com.walking.tbooking.repository.UserRepository;

public class UserService {
    private final EncodingService encodingService;

    private final UserRepository userRepository;

    public UserService(EncodingService encodingService, UserRepository userRepository) {
        this.encodingService = encodingService;
        this.userRepository = userRepository;
    }

    public User auth(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(AuthException::new);

        if (!encodingService.match(password, user.getPasswordHash())) {
            throw new AuthException();
        }

        return user;
    }

    public User create(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new DuplicateUserException();
                });

        var encodedPassword = encodingService.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);

        return userRepository.create(user);
    }

    public User update(User user) {
        var encodedPassword = encodingService.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);

        return userRepository.update(user);
    }

    public void updateLastLogin(Long id) {
        userRepository.updateLastLogin(id);
    }
}
