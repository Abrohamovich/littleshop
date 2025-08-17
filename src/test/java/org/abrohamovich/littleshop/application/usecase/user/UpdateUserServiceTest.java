package org.abrohamovich.littleshop.application.usecase.user;

import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.dto.user.UserUpdateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.abrohamovich.littleshop.domain.model.User;
import org.abrohamovich.littleshop.domain.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserServiceTest {
    private final Long userId = 1L;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private UpdateUserService updateUserService;
    private User existingUser;

    @BeforeEach
    void setUp() {
        existingUser = User.withId(userId, "John", "Doe", "john.doe@example.com", "hashed_password", UserRole.WORKER, "1234567890", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void update_shouldReturnUserResponse_whenEmailAndPhoneAreUnique() {
        UserUpdateCommand command = new UserUpdateCommand("Jane", "Smith", "jane.smith@example.com", "new_password", UserRole.WORKER, "0987654321");
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(userRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.empty());

        User updatedUser = User.withId(userId, "Jane", "Smith", "jane.smith@example.com", "new_hashed_password", UserRole.WORKER, "0987654321", existingUser.getCreatedAt(), LocalDateTime.now());
        when(userRepositoryPort.save(any(User.class))).thenReturn(updatedUser);

        UserResponse response = updateUserService.update(userId, command);

        assertNotNull(response);
        assertEquals("Jane", response.getFirstName());
        assertEquals("jane.smith@example.com", response.getEmail());
        verify(userRepositoryPort, times(1)).findById(userId);
        verify(userRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(userRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(userRepositoryPort, times(1)).save(any(User.class));
    }

    @Test
    void update_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        Long nonExistentId = 99L;
        UserUpdateCommand command = new UserUpdateCommand("Jane", "Smith", "jane.smith@example.com", "new_password", UserRole.WORKER, "0987654321");
        when(userRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                updateUserService.update(nonExistentId, command)
        );

        verify(userRepositoryPort, times(1)).findById(nonExistentId);
        verify(userRepositoryPort, never()).findByEmail(anyString());
        verify(userRepositoryPort, never()).findByPhone(anyString());
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewEmailAlreadyExists() {
        User existingUserWithEmail = User.withId(2L, "Someone", "Else", "jane.smith@example.com", "hashed", UserRole.WORKER, "0987654321", LocalDateTime.now(), LocalDateTime.now());
        UserUpdateCommand command = new UserUpdateCommand("Jane", "Smith", "jane.smith@example.com", "new_password", UserRole.WORKER, "0987654321");
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.of(existingUserWithEmail));

        assertThrows(DuplicateEntryException.class, () ->
                updateUserService.update(userId, command)
        );

        verify(userRepositoryPort, times(1)).findById(userId);
        verify(userRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(userRepositoryPort, never()).findByPhone(anyString());
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewPhoneAlreadyExists() {
        User existingUserWithPhone = User.withId(2L, "Someone", "Else", "someone.else@example.com", "hashed", UserRole.WORKER, "0987654321", LocalDateTime.now(), LocalDateTime.now());
        UserUpdateCommand command = new UserUpdateCommand("Jane", "Smith", "jane.smith@example.com", "new_password", UserRole.WORKER, "0987654321");
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(userRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.of(existingUserWithPhone));

        assertThrows(DuplicateEntryException.class, () ->
                updateUserService.update(userId, command)
        );

        verify(userRepositoryPort, times(1)).findById(userId);
        verify(userRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(userRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void update_shouldUpdateDetailsCorrectly_whenEmailAndPhoneAreUnchanged() {
        UserUpdateCommand command = new UserUpdateCommand("Jane", "Smith", "john.doe@example.com", "new_password", UserRole.WORKER, "1234567890");
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));

        User updatedUser = User.withId(userId, "Jane", "Smith", "john.doe@example.com", "new_hashed_password", UserRole.WORKER, "1234567890", existingUser.getCreatedAt(), LocalDateTime.now());
        when(userRepositoryPort.save(any(User.class))).thenReturn(updatedUser);

        UserResponse response = updateUserService.update(userId, command);

        assertNotNull(response);
        assertEquals("Jane", response.getFirstName());
        assertEquals("john.doe@example.com", response.getEmail());
        verify(userRepositoryPort, times(1)).findById(userId);
        verify(userRepositoryPort, never()).findByEmail(anyString());
        verify(userRepositoryPort, never()).findByPhone(anyString());
        verify(userRepositoryPort, times(1)).save(any(User.class));
    }
}
