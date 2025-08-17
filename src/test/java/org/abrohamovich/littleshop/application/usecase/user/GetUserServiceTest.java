package org.abrohamovich.littleshop.application.usecase.user;

import org.abrohamovich.littleshop.application.dto.user.UserResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {
    private final Long userId = 1L;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private GetUserService getUserService;
    private User testUser;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testUser = User.withId(userId, "John", "Doe", "john.doe@example.com", "hashed_password", UserRole.WORKER, "1234567890", LocalDateTime.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_shouldReturnUserResponse_whenUserExists() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(testUser));

        UserResponse response = getUserService.findById(userId);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john.doe@example.com", response.getEmail());
        verify(userRepositoryPort).findById(userId);
    }

    @Test
    void findById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> getUserService.findById(userId));
        verify(userRepositoryPort).findById(userId);
    }

    @Test
    void findAll_shouldReturnPageOfUserResponses_whenUsersExist() {
        Page<User> userPage = new PageImpl<>(List.of(testUser), pageable, 1);
        when(userRepositoryPort.findAll(pageable)).thenReturn(userPage);

        Page<UserResponse> responsePage = getUserService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("John", responsePage.getContent().get(0).getFirstName());
        verify(userRepositoryPort).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoUsersExist() {
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(userRepositoryPort.findAll(pageable)).thenReturn(emptyPage);

        Page<UserResponse> responsePage = getUserService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(userRepositoryPort).findAll(pageable);
    }

    @Test
    void findByFirstNameLike_shouldReturnPageOfUserResponses_whenMatchesExist() {
        String searchTerm = "Jo";
        Page<User> userPage = new PageImpl<>(List.of(testUser), pageable, 1);
        when(userRepositoryPort.findByFirstNameLike(searchTerm, pageable)).thenReturn(userPage);

        Page<UserResponse> responsePage = getUserService.findByFirstNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("John", responsePage.getContent().get(0).getFirstName());
        verify(userRepositoryPort).findByFirstNameLike(searchTerm, pageable);
    }

    @Test
    void findByFirstNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(userRepositoryPort.findByFirstNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<UserResponse> responsePage = getUserService.findByFirstNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(userRepositoryPort).findByFirstNameLike(searchTerm, pageable);
    }

    @Test
    void findByLastNameLike_shouldReturnPageOfUserResponses_whenMatchesExist() {
        String searchTerm = "Do";
        Page<User> userPage = new PageImpl<>(List.of(testUser), pageable, 1);
        when(userRepositoryPort.findByLastNameLike(searchTerm, pageable)).thenReturn(userPage);

        Page<UserResponse> responsePage = getUserService.findByLastNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Doe", responsePage.getContent().get(0).getLastName());
        verify(userRepositoryPort).findByLastNameLike(searchTerm, pageable);
    }

    @Test
    void findByLastNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(userRepositoryPort.findByLastNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<UserResponse> responsePage = getUserService.findByLastNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(userRepositoryPort).findByLastNameLike(searchTerm, pageable);
    }

    @Test
    void findByEmailLike_shouldReturnPageOfUserResponses_whenMatchesExist() {
        String searchTerm = "@example.com";
        Page<User> userPage = new PageImpl<>(List.of(testUser), pageable, 1);
        when(userRepositoryPort.findByEmailLike(searchTerm, pageable)).thenReturn(userPage);

        Page<UserResponse> responsePage = getUserService.findByEmailLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("john.doe@example.com", responsePage.getContent().get(0).getEmail());
        verify(userRepositoryPort).findByEmailLike(searchTerm, pageable);
    }

    @Test
    void findByEmailLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(userRepositoryPort.findByEmailLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<UserResponse> responsePage = getUserService.findByEmailLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(userRepositoryPort).findByEmailLike(searchTerm, pageable);
    }
}
