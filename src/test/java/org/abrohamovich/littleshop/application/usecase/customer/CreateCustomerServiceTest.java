package org.abrohamovich.littleshop.application.usecase.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerCreateCommand;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerServiceTest {
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @InjectMocks
    private CreateCustomerService createCustomerService;

    private CustomerCreateCommand command;

    @BeforeEach
    void setUp() {
        command = new CustomerCreateCommand("John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
    }

    @Test
    void save_shouldReturnCustomerResponse_whenCustomerIsUnique() {
        when(customerRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(customerRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.empty());
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(Customer.withId(
                1L,
                command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                command.getPhone(),
                command.getAddress(),
                LocalDateTime.now(),
                LocalDateTime.now()));

        CustomerResponse response = createCustomerService.save(command);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john.doe@example.com", response.getEmail());
        verify(customerRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(customerRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(customerRepositoryPort, times(1)).save(any(Customer.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenEmailAlreadyExists() {
        Customer existingCustomer = Customer.withId(1L, "Jane", "Smith", "john.doe@example.com", "0987654321", "456 Side Ave", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.of(existingCustomer));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createCustomerService.save(command)
        );
        assertEquals("Customer with email john.doe@example.com already exists.", exception.getMessage());
        verify(customerRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(customerRepositoryPort, never()).findByPhone(anyString());
        verify(customerRepositoryPort, never()).save(any(Customer.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenPhoneAlreadyExists() {
        Customer existingCustomer = Customer.withId(1L, "Jane", "Smith", "jane.smith@example.com", "1234567890", "456 Side Ave", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(customerRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.of(existingCustomer));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createCustomerService.save(command)
        );

        assertEquals("Customer with phone 1234567890 already exists.", exception.getMessage());
        verify(customerRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(customerRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(customerRepositoryPort, never()).save(any(Customer.class));
    }

}