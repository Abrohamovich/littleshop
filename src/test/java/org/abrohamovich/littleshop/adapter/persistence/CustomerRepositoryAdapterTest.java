package org.abrohamovich.littleshop.adapter.persistence;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CustomerJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataCustomerRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.CustomerJpaMapper;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.Customer;
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
class CustomerRepositoryAdapterTest {
    @Mock
    private SpringDataCustomerRepository springDataCustomerRepository;
    @Mock
    private CustomerJpaMapper customerJpaMapper;
    @InjectMocks
    private CustomerRepositoryAdapter customerRepositoryAdapter;

    private Customer testCustomer;
    private CustomerJpaEntity testEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.withId(1L, "John", "Doe", "john.doe@gmail.com",
                "1234567890", "Somewhere in NY", LocalDateTime.now(), LocalDateTime.now());
        testEntity = new CustomerJpaEntity(1L, "John", "Doe", "john.doe@gmail.com",
                "1234567890", "Somewhere in NY", LocalDateTime.now(), LocalDateTime.now());
        pageable = Pageable.unpaged();
    }

    @Test
    void save_shouldReturnSavedCustomer_whenSaveIsSuccessful() {
        when(customerJpaMapper.toJpaEntity(any(Customer.class))).thenReturn(testEntity);
        when(springDataCustomerRepository.save(any(CustomerJpaEntity.class))).thenReturn(testEntity);
        when(customerJpaMapper.toDomainEntity(any(CustomerJpaEntity.class))).thenReturn(testCustomer);

        Customer savedCustomer = customerRepositoryAdapter.save(testCustomer);

        assertNotNull(savedCustomer);
        assertEquals(testCustomer.getId(), savedCustomer.getId());
        assertEquals(testCustomer.getEmail(), savedCustomer.getEmail());
        verify(springDataCustomerRepository).save(testEntity);
        verify(customerJpaMapper).toDomainEntity(testEntity);
    }

    @Test
    void save_shouldThrowDataPersistenceException_onDataAccessException() {
        when(customerJpaMapper.toJpaEntity(any(Customer.class))).thenReturn(testEntity);
        when(springDataCustomerRepository.save(any(CustomerJpaEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> customerRepositoryAdapter.save(testCustomer));
        assertTrue(exception.getMessage().contains("Failed to save customer due to data integrity violation."));
    }

    @Test
    void save_shouldThrowDataPersistenceException_onGenericException() {
        when(customerJpaMapper.toJpaEntity(any(Customer.class))).thenThrow(new RuntimeException("Mapper error"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> customerRepositoryAdapter.save(testCustomer));
        assertTrue(exception.getMessage().contains("Failed to save customer."));
    }

    @Test
    void findById_shouldReturnCustomer_whenFound() {
        when(springDataCustomerRepository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Optional<Customer> foundCustomer = customerRepositoryAdapter.findById(1L);

        assertTrue(foundCustomer.isPresent());
        assertEquals(testCustomer.getId(), foundCustomer.get().getId());
        verify(springDataCustomerRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataCustomerRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Customer> foundCustomer = customerRepositoryAdapter.findById(99L);

        assertFalse(foundCustomer.isPresent());
    }

    @Test
    void findByEmail_shouldReturnCustomer_whenFound() {
        when(springDataCustomerRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testEntity));
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Optional<Customer> foundCustomer = customerRepositoryAdapter.findByEmail("john.doe@example.com");

        assertTrue(foundCustomer.isPresent());
        assertEquals(testCustomer.getEmail(), foundCustomer.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataCustomerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<Customer> foundCustomer = customerRepositoryAdapter.findByEmail("nonexistent@example.com");

        assertFalse(foundCustomer.isPresent());
    }

    @Test
    void findByPhone_shouldReturnCustomer_whenFound() {
        when(springDataCustomerRepository.findByPhone("1234567890")).thenReturn(Optional.of(testEntity));
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Optional<Customer> foundCustomer = customerRepositoryAdapter.findByPhone("1234567890");

        assertTrue(foundCustomer.isPresent());
        assertEquals(testCustomer.getPhone(), foundCustomer.get().getPhone());
    }

    @Test
    void findByPhone_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataCustomerRepository.findByPhone("0000000000")).thenReturn(Optional.empty());

        Optional<Customer> foundCustomer = customerRepositoryAdapter.findByPhone("0000000000");

        assertFalse(foundCustomer.isPresent());
    }

    @Test
    void findAll_shouldReturnPageOfCustomers() {
        Page<CustomerJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        Page<Customer> expectedPage = new PageImpl<>(Collections.singletonList(testCustomer));
        when(springDataCustomerRepository.findAll(pageable)).thenReturn(entityPage);
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Page<Customer> resultPage = customerRepositoryAdapter.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCustomer.getFirstName(), resultPage.getContent().get(0).getFirstName());
    }

    @Test
    void findByFirstNameLike_shouldReturnPageOfCustomers() {
        Page<CustomerJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataCustomerRepository.findByFirstNameContainingIgnoreCase("john", pageable)).thenReturn(entityPage);
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Page<Customer> resultPage = customerRepositoryAdapter.findByFirstNameLike("john", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCustomer.getFirstName(), resultPage.getContent().get(0).getFirstName());
    }

    @Test
    void findByLastNameLike_shouldReturnPageOfCustomers() {
        Page<CustomerJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataCustomerRepository.findByLastNameContainingIgnoreCase("doe", pageable)).thenReturn(entityPage);
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Page<Customer> resultPage = customerRepositoryAdapter.findByLastNameLike("doe", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCustomer.getLastName(), resultPage.getContent().get(0).getLastName());
    }

    @Test
    void findByEmailLike_shouldReturnPageOfCustomers() {
        Page<CustomerJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        when(springDataCustomerRepository.findByEmailContainingIgnoreCase("example", pageable)).thenReturn(entityPage);
        when(customerJpaMapper.toDomainEntity(testEntity)).thenReturn(testCustomer);

        Page<Customer> resultPage = customerRepositoryAdapter.findByEmailLike("example", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCustomer.getEmail(), resultPage.getContent().get(0).getEmail());
    }

    @Test
    void deleteById_shouldSucceed_whenCustomerExists() {
        doNothing().when(springDataCustomerRepository).deleteById(1L);

        assertDoesNotThrow(() -> customerRepositoryAdapter.deleteById(1L));

        verify(springDataCustomerRepository).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowDataPersistenceException_onException() {
        doThrow(new RuntimeException("DB error")).when(springDataCustomerRepository).deleteById(1L);

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> customerRepositoryAdapter.deleteById(1L));
        assertTrue(exception.getMessage().contains("Failed to delete customer with ID '1'."));
    }

}