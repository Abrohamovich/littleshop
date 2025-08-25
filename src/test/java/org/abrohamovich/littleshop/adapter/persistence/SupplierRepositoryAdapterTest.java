package org.abrohamovich.littleshop.adapter.persistence;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.SupplierJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataSupplierRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.SupplierJpaMapper;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.Supplier;
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
class SupplierRepositoryAdapterTest {
    @Mock
    private SpringDataSupplierRepository springDataSupplierRepository;
    @Mock
    private SupplierJpaMapper supplierJpaMapper;
    @InjectMocks
    private SupplierRepositoryAdapter supplierRepositoryAdapter;

    private Supplier testSupplier;
    private SupplierJpaEntity testEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testSupplier = Supplier.withId(1L, "Some LTD", "some.ltd@example.com", "+1 44 390-13-92",
                "Somewhere in LA", "Electronics", LocalDateTime.now(), LocalDateTime.now());
        testEntity = new SupplierJpaEntity(1L, "Some LTD", "some.ltd@example.com", "+1 44 390-13-92",
                "Somewhere in LA", "Electronics", LocalDateTime.now(), LocalDateTime.now());
        pageable = Pageable.unpaged();
    }

    @Test
    void save_shouldReturnSavedSupplier_whenSaveIsSuccessful() {
        when(supplierJpaMapper.toJpaEntity(any(Supplier.class))).thenReturn(testEntity);
        when(springDataSupplierRepository.save(any(SupplierJpaEntity.class))).thenReturn(testEntity);
        when(supplierJpaMapper.toDomainEntity(any(SupplierJpaEntity.class))).thenReturn(testSupplier);

        Supplier savedSupplier = supplierRepositoryAdapter.save(testSupplier);

        assertNotNull(savedSupplier);
        assertEquals(testSupplier.getId(), savedSupplier.getId());
        assertEquals(testSupplier.getName(), savedSupplier.getName());
        verify(springDataSupplierRepository).save(testEntity);
        verify(supplierJpaMapper).toDomainEntity(testEntity);
    }

    @Test
    void save_shouldThrowDataPersistenceException_onDataAccessException() {
        when(supplierJpaMapper.toJpaEntity(any(Supplier.class))).thenReturn(testEntity);
        when(springDataSupplierRepository.save(any(SupplierJpaEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate key"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> supplierRepositoryAdapter.save(testSupplier));
        assertTrue(exception.getMessage().contains("Failed to save supplier due to data integrity violation."));
    }

    @Test
    void save_shouldThrowDataPersistenceException_onGenericException() {
        when(supplierJpaMapper.toJpaEntity(any(Supplier.class))).thenThrow(new RuntimeException("Mapper error"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> supplierRepositoryAdapter.save(testSupplier));
        assertTrue(exception.getMessage().contains("Failed to save supplier."));
    }

    @Test
    void findById_shouldReturnSupplier_whenFound() {
        when(springDataSupplierRepository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findById(1L);

        assertTrue(foundSupplier.isPresent());
        assertEquals(testSupplier.getId(), foundSupplier.get().getId());
        verify(springDataSupplierRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataSupplierRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findById(99L);

        assertFalse(foundSupplier.isPresent());
    }

    @Test
    void findByName_shouldReturnSupplier_whenFound() {
        when(springDataSupplierRepository.findByName("Some LTD")).thenReturn(Optional.of(testEntity));
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findByName("Some LTD");

        assertTrue(foundSupplier.isPresent());
        assertEquals(testSupplier.getName(), foundSupplier.get().getName());
    }

    @Test
    void findByName_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataSupplierRepository.findByName("Non-existent")).thenReturn(Optional.empty());

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findByName("Non-existent");

        assertFalse(foundSupplier.isPresent());
    }

    @Test
    void findByEmail_shouldReturnSupplier_whenFound() {
        when(springDataSupplierRepository.findByEmail("some.ltd@example.com")).thenReturn(Optional.of(testEntity));
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findByEmail("some.ltd@example.com");

        assertTrue(foundSupplier.isPresent());
        assertEquals(testSupplier.getEmail(), foundSupplier.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataSupplierRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findByEmail("nonexistent@example.com");

        assertFalse(foundSupplier.isPresent());
    }

    @Test
    void findByPhone_shouldReturnSupplier_whenFound() {
        when(springDataSupplierRepository.findByPhone("+1 44 390-13-92")).thenReturn(Optional.of(testEntity));
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findByPhone("+1 44 390-13-92");

        assertTrue(foundSupplier.isPresent());
        assertEquals(testSupplier.getPhone(), foundSupplier.get().getPhone());
    }

    @Test
    void findByPhone_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataSupplierRepository.findByPhone("0000000000")).thenReturn(Optional.empty());

        Optional<Supplier> foundSupplier = supplierRepositoryAdapter.findByPhone("0000000000");

        assertFalse(foundSupplier.isPresent());
    }

    @Test
    void findAll_shouldReturnPageOfSuppliers() {
        Page<SupplierJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        Page<Supplier> expectedPage = new PageImpl<>(Collections.singletonList(testSupplier));
        when(springDataSupplierRepository.findAll(pageable)).thenReturn(entityPage);
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Page<Supplier> resultPage = supplierRepositoryAdapter.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testSupplier.getName(), resultPage.getContent().get(0).getName());
    }

    @Test
    void findByNameLike_shouldReturnPageOfSuppliers() {
        Page<SupplierJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataSupplierRepository.findByNameContainingIgnoreCase("some", pageable)).thenReturn(entityPage);
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Page<Supplier> resultPage = supplierRepositoryAdapter.findByNameLike("some", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testSupplier.getName(), resultPage.getContent().get(0).getName());
    }

    @Test
    void findByEmailLike_shouldReturnPageOfSuppliers() {
        Page<SupplierJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataSupplierRepository.findByEmailContainingIgnoreCase("example", pageable)).thenReturn(entityPage);
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Page<Supplier> resultPage = supplierRepositoryAdapter.findByEmailLike("example", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testSupplier.getEmail(), resultPage.getContent().get(0).getEmail());
    }

    @Test
    void findByPhoneLike_shouldReturnPageOfSuppliers() {
        Page<SupplierJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataSupplierRepository.findByPhoneContainingIgnoreCase("390", pageable)).thenReturn(entityPage);
        when(supplierJpaMapper.toDomainEntity(testEntity)).thenReturn(testSupplier);

        Page<Supplier> resultPage = supplierRepositoryAdapter.findByPhoneLike("390", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testSupplier.getPhone(), resultPage.getContent().get(0).getPhone());
    }

    @Test
    void deleteById_shouldSucceed_whenSupplierExists() {
        doNothing().when(springDataSupplierRepository).deleteById(1L);

        assertDoesNotThrow(() -> supplierRepositoryAdapter.deleteById(1L));

        verify(springDataSupplierRepository).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowDataPersistenceException_onDataAccessException() {
        doThrow(new DataIntegrityViolationException("Cannot delete")).when(springDataSupplierRepository).deleteById(1L);

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> supplierRepositoryAdapter.deleteById(1L));
        assertTrue(exception.getMessage().contains("Failed to delete supplier with ID '1' due to data access error."));
    }

    @Test
    void deleteById_shouldThrowDataPersistenceException_onGenericException() {
        doThrow(new RuntimeException("DB error")).when(springDataSupplierRepository).deleteById(1L);

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> supplierRepositoryAdapter.deleteById(1L));
        assertTrue(exception.getMessage().contains("Failed to delete supplier with ID '1'."));
    }
}