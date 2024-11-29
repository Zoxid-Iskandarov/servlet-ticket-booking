package com.walking.tbooking.service;

import com.walking.tbooking.domain.users.Role;
import com.walking.tbooking.domain.users.User;
import com.walking.tbooking.exception.AuthException;
import com.walking.tbooking.exception.DuplicateUserException;
import com.walking.tbooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private EncodingService encodingService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void auth_success() {
        var user = getUser();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(anyString());
        doReturn(true).when(encodingService).match(anyString(), anyString());

        User actual = userService.auth(user.getEmail(), user.getPasswordHash());

        assertNotNull(actual);
        assertEquals(user, actual);

        verify(userRepository).findByEmail(anyString());
        verify(encodingService).match(anyString(), anyString());
    }

    @Test
    void auth_emailNotFound_failed() {
        var user = getUser();

        doThrow(AuthException.class).when(userRepository).findByEmail(anyString());

        Executable executable = () -> userService.auth(user.getEmail(), user.getPasswordHash());

        assertThrows(AuthException.class, executable);

        verify(userRepository).findByEmail(anyString());
        verify(encodingService, never()).match(anyString(), anyString());
    }

    @Test
    void auth_invalidPassword_failed() {
        var user = getUser();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(anyString());
        doReturn(false).when(encodingService).match(anyString(), anyString());

        Executable executable = () -> userService.auth(user.getEmail(), user.getPasswordHash());

        assertThrows(AuthException.class, executable);

        verify(userRepository).findByEmail(anyString());
        verify(encodingService).match(anyString(), anyString());
    }

    @Test
    void auth_repositoryError_failed() {
        var user = getUser();

        doThrow(RuntimeException.class).when(userRepository).findByEmail(anyString());

        Executable executable = () -> userService.auth(user.getEmail(), user.getPasswordHash());

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).findByEmail(anyString());
        verify(encodingService, never()).match(anyString(), anyString());
    }

    @Test
    void create_success() {
        var user = getUser();
        var createdUser = getUser();
        var encodedPassword = "test_password_hash";

        doReturn(Optional.empty()).when(userRepository).findByEmail(user.getEmail());
        doReturn(encodedPassword).when(encodingService).encode(user.getPasswordHash());
        doReturn(createdUser).when(userRepository).create(user);

        var actual = userService.create(user);

        assertNotNull(actual);
        assertEquals(user.getId(), actual.getId());
        assertNotEquals(user.getPasswordHash(), actual.getPasswordHash());

        verify(userRepository).findByEmail(any());
        verify(encodingService).encode(anyString());
        verify(userRepository).create(user);
    }

    @Test
    void create_duplicateEmail_failed() {
        var user = getUser();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

        Executable executable = () -> userService.create(user);

        assertThrows(DuplicateUserException.class, executable);

        verify(userRepository).findByEmail(any());
        verify(encodingService, never()).encode(anyString());
        verify(userRepository, never()).create(user);
    }

    @Test
    void create_repositoryError_failed() {
        var user = getUser();

        doReturn(Optional.empty()).when(userRepository).findByEmail(user.getEmail());
        doReturn("test_password_hash").when(encodingService).encode(user.getPasswordHash());

        doThrow(RuntimeException.class).when(userRepository).create(user);

        Executable executable = () -> userService.create(user);

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).findByEmail(any());
        verify(encodingService).encode(anyString());
        verify(userRepository).create(user);
    }

    @Test
    void delete_success() {
        doReturn(true).when(userRepository).deleteById(1L);

        var actual = userService.delete(1L);

        assertTrue(actual);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_userNotFound() {
        doReturn(false).when(userRepository).deleteById(1L);

        var actual = userService.delete(1L);

        assertFalse(actual);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_repositoryError_failed() {
        doThrow(RuntimeException.class).when(userRepository).deleteById(1L);

        Executable executable = () -> userService.delete(1L);

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void updateLastLogin_success() {
        doNothing().when(userRepository).updateLastLogin(1L);

        userService.updateLastLogin(1L);

        verify(userRepository).updateLastLogin(1L);
    }

    @Test
    void updateLastLogin_repositoryError_failed() {
        doThrow(RuntimeException.class).when(userRepository).updateLastLogin(1L);

        Executable executable = () -> userService.updateLastLogin(1L);

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).updateLastLogin(anyLong());
    }

    @Test
    void blockUser_success() {
        doReturn(true).when(userRepository).updateStatus(1L, true);

        var actual = userService.blockUser(1L);

        assertTrue(actual);

        verify(userRepository).updateStatus(1L, true);
    }

    @Test
    void blockUser_userNotFound() {
        doReturn(false).when(userRepository).updateStatus(1L, true);

        var actual = userService.blockUser(1L);

        assertFalse(actual);

        verify(userRepository).updateStatus(1L, true);
    }

    @Test
    void blockUser_repositoryError_failed() {
        doThrow(RuntimeException.class).when(userRepository).updateStatus(1L, true);

        Executable executable = () -> userService.blockUser(1L);

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).updateStatus(1L, true);
    }

    @Test
    void unblockUser_success() {
        doReturn(true).when(userRepository).updateStatus(1L, false);

        var actual = userService.unblockUser(1L);

        assertTrue(actual);

        verify(userRepository).updateStatus(1L, false);
    }

    @Test
    void unblockUser_userNotFound() {
        doReturn(false).when(userRepository).updateStatus(1L, false);

        var actual = userService.unblockUser(1L);

        assertFalse(actual);

        verify(userRepository).updateStatus(1L, false);
    }

    @Test
    void unblockUser_repositoryError_failed() {
        doThrow(RuntimeException.class).when(userRepository).updateStatus(1L, false);

        Executable executable = () -> userService.unblockUser(1L);

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).updateStatus(1L, false);
    }

    @Test
    void userBlocker_value_success() {
        doReturn(false).when(userRepository).isUserBlocked(1L);

        var actual = userService.userBlocked(1L);

        assertFalse(actual);

        verify(userRepository).isUserBlocked(1L);
    }

    @Test
    void userBlocker_repositoryError_failed() {
        doThrow(RuntimeException.class).when(userRepository).isUserBlocked(1L);

        Executable executable = () -> userService.userBlocked(1L);

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).isUserBlocked(1L);
    }

    @Test
    void adminPresent_success() {
        doReturn(true).when(userRepository).adminPresent();

        var actual = userService.adminPresent();

        assertTrue(actual);

        verify(userRepository).adminPresent();
    }

    @Test
    void adminPresent_notFound_success() {
        doReturn(false).when(userRepository).adminPresent();

        var actual = userService.adminPresent();

        assertFalse(actual);

        verify(userRepository).adminPresent();
    }

    @Test
    void adminPresent_repositoryError_failed() {
        doThrow(RuntimeException.class).when(userRepository).adminPresent();

        Executable executable = () -> userService.adminPresent();

        assertThrows(RuntimeException.class, executable);

        verify(userRepository).adminPresent();
    }

    private User getUser() {
        User user = new User();

        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPasswordHash("test_password");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPatronymic("test");
        user.setRole(Role.USER);

        return user;
    }
}