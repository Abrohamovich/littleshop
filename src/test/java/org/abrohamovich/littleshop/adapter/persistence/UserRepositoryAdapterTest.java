package org.abrohamovich.littleshop.adapter.persistence;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.UserJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataUserRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.UserJpaMapper;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.User;
import org.abrohamovich.littleshop.domain.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {
    @Mock
    private SpringDataUserRepository springDataUserRepository;
    @Mock
    private UserJpaMapper userJpaMapper;
    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    private User testUser;
    private UserJpaEntity testEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testUser = User.withId(1L, "Steve", "Witkoff", "steve.witkoff@example.com",
                "OI#)f03bg903igh4h", UserRole.WORKER, "+1 80 947-88-46",
                LocalDateTime.now(), LocalDateTime.now());
        testEntity = new UserJpaEntity(1L, "Steve", "Witkoff", "steve.witkoff@example.com",
                "OI#)f03bg903igh4h", UserRole.WORKER, "+1 80 947-88-46",
                LocalDateTime.now(), LocalDateTime.now());
        pageable = Pageable.unpaged();
    }

    @Test
    void save_shouldReturnSavedUser_whenSaveIsSuccessful() {
        when(userJpaMapper.toJpaEntity(any(User.class))).thenReturn(testEntity);
        when(springDataUserRepository.save(any(UserJpaEntity.class))).thenReturn(testEntity);
        when(userJpaMapper.toDomainEntity(any(UserJpaEntity.class))).thenReturn(testUser);

        User savedUser = userRepositoryAdapter.save(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        verify(springDataUserRepository).save(testEntity);
        verify(userJpaMapper).toDomainEntity(testEntity);
    }

    @Test
    void save_shouldThrowDataPersistenceException_onDataAccessException() {
        when(userJpaMapper.toJpaEntity(any(User.class))).thenReturn(testEntity);
        when(springDataUserRepository.save(any(UserJpaEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate key"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> userRepositoryAdapter.save(testUser));
        assertTrue(exception.getMessage().contains("Failed to save user due to data integrity violation."));
    }

    @Test
    void save_shouldThrowDataPersistenceException_onGenericException() {
        when(userJpaMapper.toJpaEntity(any(User.class))).thenThrow(new RuntimeException("Mapper error"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> userRepositoryAdapter.save(testUser));
        assertTrue(exception.getMessage().contains("Failed to save user."));
    }

    @Test
    void findById_shouldReturnUser_whenFound() {
        when(springDataUserRepository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Optional<User> foundUser = userRepositoryAdapter.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getId(), foundUser.get().getId());
        verify(springDataUserRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataUserRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepositoryAdapter.findById(99L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByEmail_shouldReturnUser_whenFound() {
        when(springDataUserRepository.findByEmail("steve.witkoff@example.com")).thenReturn(Optional.of(testEntity));
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Optional<User> foundUser = userRepositoryAdapter.findByEmail("steve.witkoff@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataUserRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepositoryAdapter.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByPhone_shouldReturnUser_whenFound() {
        when(springDataUserRepository.findByPhone("+1 80 947-88-46")).thenReturn(Optional.of(testEntity));
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Optional<User> foundUser = userRepositoryAdapter.findByPhone("+1 80 947-88-46");

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getPhone(), foundUser.get().getPhone());
    }

    @Test
    void findByPhone_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataUserRepository.findByPhone("0000000000")).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepositoryAdapter.findByPhone("0000000000");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAll_shouldReturnPageOfUsers() {
        Page<UserJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataUserRepository.findAll(pageable)).thenReturn(entityPage);
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Page<User> resultPage = userRepositoryAdapter.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testUser.getFirstName(), resultPage.getContent().get(0).getFirstName());
    }

    @Test
    void findByFirstNameLike_shouldReturnPageOfUsers() {
        Page<UserJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataUserRepository.findByFirstNameContainingIgnoreCase("steve", pageable)).thenReturn(entityPage);
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Page<User> resultPage = userRepositoryAdapter.findByFirstNameLike("steve", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testUser.getFirstName(), resultPage.getContent().get(0).getFirstName());
    }

    @Test
    void findByLastNameLike_shouldReturnPageOfUsers() {
        Page<UserJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataUserRepository.findByLastNameContainingIgnoreCase("witkoff", pageable)).thenReturn(entityPage);
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Page<User> resultPage = userRepositoryAdapter.findByLastNameLike("witkoff", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testUser.getLastName(), resultPage.getContent().get(0).getLastName());
    }

    @Test
    void findByEmailLike_shouldReturnPageOfUsers() {
        Page<UserJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataUserRepository.findByEmailContainingIgnoreCase("example", pageable)).thenReturn(entityPage);
        when(userJpaMapper.toDomainEntity(testEntity)).thenReturn(testUser);

        Page<User> resultPage = userRepositoryAdapter.findByEmailLike("example", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testUser.getEmail(), resultPage.getContent().get(0).getEmail());
    }

    @Test
    void deleteById_shouldSucceed_whenUserExists() {
        doNothing().when(springDataUserRepository).deleteById(1L);

        assertDoesNotThrow(() -> userRepositoryAdapter.deleteById(1L));

        verify(springDataUserRepository).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowDataPersistenceException_onException() {
        doThrow(new RuntimeException("DB error")).when(springDataUserRepository).deleteById(1L);

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> userRepositoryAdapter.deleteById(1L));
        assertTrue(exception.getMessage().contains("Failed to delete user with ID '1'."));
    }
}
