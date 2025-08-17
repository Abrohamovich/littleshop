package org.abrohamovich.littleshop.application.usecase.customer;

import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerServiceTest {
    private final Long customerId = 1L;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @InjectMocks
    private DeleteCustomerService deleteCustomerService;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.withId(customerId, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void deleteById_shouldDeleteCustomer_whenCustomerExists() {
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(testCustomer));

        deleteCustomerService.deleteById(customerId);

        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(customerRepositoryPort, times(1)).deleteById(customerId);
    }

    @Test
    void deleteById_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        Long nonExistentId = 99L;
        when(customerRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () ->
                deleteCustomerService.deleteById(nonExistentId)
        );

        assertEquals("Category with ID " + nonExistentId + " not found for deletion.", exception.getMessage());
        verify(customerRepositoryPort, times(1)).findById(nonExistentId);
        verify(customerRepositoryPort, never()).deleteById(anyLong());
    }
}