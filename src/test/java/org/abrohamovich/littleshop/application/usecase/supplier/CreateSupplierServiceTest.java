package org.abrohamovich.littleshop.application.usecase.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierCreateCommand;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.Supplier;
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
public class CreateSupplierServiceTest {
    @Mock
    private SupplierRepositoryPort supplierRepositoryPort;
    @InjectMocks
    private CreateSupplierService createSupplierService;

    private SupplierCreateCommand command;

    @BeforeEach
    void setUp() {
        command = new SupplierCreateCommand("Test Supplier", "test@example.com", "1234567890", "123 Main St", "Test description");
    }

    @Test
    void save_shouldReturnSupplierResponse_whenSupplierIsUnique() {
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.save(any(Supplier.class))).thenReturn(Supplier.withId(
                1L,
                command.getName(),
                command.getEmail(),
                command.getPhone(),
                command.getAddress(),
                command.getDescription(),
                LocalDateTime.now(),
                LocalDateTime.now()));

        SupplierResponse response = createSupplierService.save(command);

        assertNotNull(response);
        assertEquals("Test Supplier", response.getName());
        assertEquals("test@example.com", response.getEmail());
        verify(supplierRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(supplierRepositoryPort, times(1)).findByName(command.getName());
        verify(supplierRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(supplierRepositoryPort, times(1)).save(any(Supplier.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenEmailAlreadyExists() {
        Supplier existingSupplier = Supplier.withId(1L, "Jane Smith", "test@example.com", "0987654321", "456 Side Ave", "Old", LocalDateTime.now(), LocalDateTime.now());
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.of(existingSupplier));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createSupplierService.save(command)
        );
        assertEquals("Customer with email '" + command.getEmail() + "' already exists.", exception.getMessage());
        verify(supplierRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(supplierRepositoryPort, never()).findByName(anyString());
        verify(supplierRepositoryPort, never()).findByPhone(anyString());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenNameAlreadyExists() {
        Supplier existingSupplier = Supplier.withId(1L, "Test Supplier", "other@example.com", "0987654321", "456 Side Ave", "Old", LocalDateTime.now(), LocalDateTime.now());
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.of(existingSupplier));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createSupplierService.save(command)
        );
        assertEquals("Customer with name '" + command.getName() + "' already exists.", exception.getMessage());
        verify(supplierRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(supplierRepositoryPort, times(1)).findByName(command.getName());
        verify(supplierRepositoryPort, never()).findByPhone(anyString());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenPhoneAlreadyExists() {
        Supplier existingSupplier = Supplier.withId(1L, "Another Supplier", "another@example.com", "1234567890", "456 Side Ave", "Old", LocalDateTime.now(), LocalDateTime.now());
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.of(existingSupplier));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createSupplierService.save(command)
        );
        assertEquals("Customer with phone '" + command.getPhone() + "' already exists.", exception.getMessage());
        verify(supplierRepositoryPort, times(1)).findByEmail(command.getEmail());
        verify(supplierRepositoryPort, times(1)).findByName(command.getName());
        verify(supplierRepositoryPort, times(1)).findByPhone(command.getPhone());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }
}