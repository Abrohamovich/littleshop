package org.abrohamovich.littleshop.application.usecase.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.dto.customer.CustomerUpdateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCustomerServiceTest {
    private final Long customerId = 1L;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @InjectMocks
    private UpdateCustomerService updateCustomerService;
    private Customer existingCustomer;
    private CustomerUpdateCommand updateCommand;

    @BeforeEach
    void setUp() {
        existingCustomer = Customer.withId(customerId, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void update_shouldReturnCustomerResponse_whenEmailAndPhoneAreUnique() {
        updateCommand = new CustomerUpdateCommand("Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave");
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepositoryPort.findByEmail(updateCommand.getEmail())).thenReturn(Optional.empty());
        when(customerRepositoryPort.findByPhone(updateCommand.getPhone())).thenReturn(Optional.empty());
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(Customer.withId(customerId, "Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave", existingCustomer.getCreatedAt(), LocalDateTime.now()));

        CustomerResponse response = updateCustomerService.update(customerId, updateCommand);

        assertNotNull(response);
        assertEquals("Jane", response.getFirstName());
        assertEquals("jane.smith@example.com", response.getEmail());
        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(customerRepositoryPort, times(1)).findByEmail(updateCommand.getEmail());
        verify(customerRepositoryPort, times(1)).findByPhone(updateCommand.getPhone());
        verify(customerRepositoryPort, times(1)).save(any(Customer.class));
    }

    @Test
    void update_shouldReturnCustomerResponse_whenOnlyOtherDetailsAreChanged() {
        updateCommand = new CustomerUpdateCommand("Jane", "Smith", "john.doe@example.com", "1234567890", "456 Side Ave");
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(Customer.withId(customerId, "Jane", "Smith", "john.doe@example.com", "1234567890", "456 Side Ave", existingCustomer.getCreatedAt(), LocalDateTime.now()));

        CustomerResponse response = updateCustomerService.update(customerId, updateCommand);

        assertNotNull(response);
        assertEquals("Jane", response.getFirstName());
        assertEquals("john.doe@example.com", response.getEmail());
        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(customerRepositoryPort, never()).findByEmail(anyString());
        verify(customerRepositoryPort, never()).findByPhone(anyString());
        verify(customerRepositoryPort, times(1)).save(any(Customer.class));
    }

    @Test
    void update_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        Long nonExistentId = 99L;
        updateCommand = new CustomerUpdateCommand("Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave");
        when(customerRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () ->
                updateCustomerService.update(nonExistentId, updateCommand)
        );

        assertEquals("Customer with ID " + nonExistentId + " not found for update.", exception.getMessage());
        verify(customerRepositoryPort, times(1)).findById(nonExistentId);
        verify(customerRepositoryPort, never()).findByEmail(anyString());
        verify(customerRepositoryPort, never()).findByPhone(anyString());
        verify(customerRepositoryPort, never()).save(any(Customer.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewEmailAlreadyExists() {
        Customer existingDuplicateCustomer = Customer.withId(2L, "Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave", LocalDateTime.now(), LocalDateTime.now());
        updateCommand = new CustomerUpdateCommand("Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave");

        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepositoryPort.findByEmail(updateCommand.getEmail())).thenReturn(Optional.of(existingDuplicateCustomer));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                updateCustomerService.update(customerId, updateCommand)
        );

        assertEquals("Customer with email 'jane.smith@example.com' already exists.", exception.getMessage());
        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(customerRepositoryPort, times(1)).findByEmail(updateCommand.getEmail());
        verify(customerRepositoryPort, never()).findByPhone(anyString());
        verify(customerRepositoryPort, never()).save(any(Customer.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewPhoneAlreadyExists() {
        Customer existingDuplicateCustomer = Customer.withId(2L, "Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave", LocalDateTime.now(), LocalDateTime.now());
        updateCommand = new CustomerUpdateCommand("Jane", "Smith", "jane.smith@example.com", "0987654321", "456 Side Ave");

        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepositoryPort.findByEmail(updateCommand.getEmail())).thenReturn(Optional.empty());
        when(customerRepositoryPort.findByPhone(updateCommand.getPhone())).thenReturn(Optional.of(existingDuplicateCustomer));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                updateCustomerService.update(customerId, updateCommand)
        );

        assertEquals("Customer with phone '0987654321' already exists.", exception.getMessage());
        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(customerRepositoryPort, times(1)).findByEmail(updateCommand.getEmail());
        verify(customerRepositoryPort, times(1)).findByPhone(updateCommand.getPhone());
        verify(customerRepositoryPort, never()).save(any(Customer.class));
    }
}