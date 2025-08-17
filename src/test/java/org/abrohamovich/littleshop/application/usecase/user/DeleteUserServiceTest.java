package org.abrohamovich.littleshop.application.usecase.user;

import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserServiceTest {
    private final Long userId = 1L;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private DeleteUserService deleteUserService;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.withId(userId, "John", "Doe", "john.doe@example.com", "hashed_password", UserRole.WORKER, "1234567890", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void deleteById_shouldDeleteUser_whenUserExists() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(testUser));

        deleteUserService.deleteById(userId);

        verify(userRepositoryPort, times(1)).findById(userId);
        verify(userRepositoryPort, times(1)).deleteById(userId);
    }

    @Test
    void deleteById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        Long nonExistentId = 99L;
        when(userRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                deleteUserService.deleteById(nonExistentId)
        );

        verify(userRepositoryPort, times(1)).findById(nonExistentId);
        verify(userRepositoryPort, never()).deleteById(anyLong());
    }
}
