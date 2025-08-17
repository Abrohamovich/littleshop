package org.abrohamovich.littleshop.application.usecase.user;

import org.abrohamovich.littleshop.application.dto.user.UserCreateCommand;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
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
public class CreateUserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private CreateUserService createUserService;

    private UserCreateCommand command;

    @BeforeEach
    void setUp() {
        command = new UserCreateCommand("John", "Doe", "john.doe@example.com", "password123", UserRole.WORKER, "1234567890");
    }

    @Test
    void save_shouldReturnUserResponse_whenUserIsUnique() {
        when(userRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(userRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.empty());

        User savedUser = User.withId(1L, command.getFirstName(), command.getLastName(), command.getEmail(), "hashed_password", command.getRole(), command.getPhone(), LocalDateTime.now(), LocalDateTime.now());
        when(userRepositoryPort.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = createUserService.save(command);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("john.doe@example.com", response.getEmail());
        verify(userRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(userRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(userRepositoryPort, times(1)).save(any(User.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenEmailAlreadyExists() {
        User existingUser = User.withId(1L, "Jane", "Doe", "john.doe@example.com", "hashed", UserRole.WORKER, "0987654321", LocalDateTime.now(), LocalDateTime.now());
        when(userRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.of(existingUser));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createUserService.save(command)
        );

        assertEquals("User with email '" + command.getEmail() + "' already exists.", exception.getMessage());
        verify(userRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(userRepositoryPort, never()).findByPhone(anyString());
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenPhoneAlreadyExists() {
        User existingUser = User.withId(1L, "Jane", "Doe", "jane.doe@example.com", "hashed", UserRole.WORKER, "1234567890", LocalDateTime.now(), LocalDateTime.now());
        when(userRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(userRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.of(existingUser));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createUserService.save(command)
        );

        assertEquals("User with phone '" + command.getPhone() + "' already exists.", exception.getMessage());
        verify(userRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(userRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(userRepositoryPort, never()).save(any(User.class));
    }
}
