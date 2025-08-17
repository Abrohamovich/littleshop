package org.abrohamovich.littleshop.application.usecase.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierUpdateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
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
public class UpdateSupplierServiceTest {
    private final Long supplierId = 1L;
    @Mock
    private SupplierRepositoryPort supplierRepositoryPort;
    @InjectMocks
    private UpdateSupplierService updateSupplierService;
    private Supplier existingSupplier;

    @BeforeEach
    void setUp() {
        existingSupplier = Supplier.withId(supplierId, "Original Supplier", "original@example.com", "1234567890", "123 Main St", "Original description", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void update_shouldReturnSupplierResponse_whenAllDetailsAreUnique() {
        SupplierUpdateCommand command = new SupplierUpdateCommand("New Supplier", "new@example.com", "0987654321", "456 Side Ave", "New description");
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.save(any(Supplier.class))).thenReturn(Supplier.withId(supplierId, "New Supplier", "new@example.com", "0987654321", "456 Side Ave", "New description", existingSupplier.getCreatedAt(), LocalDateTime.now()));

        SupplierResponse response = updateSupplierService.update(supplierId, command);

        assertNotNull(response);
        assertEquals("New Supplier", response.getName());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("0987654321", response.getPhone());
        verify(supplierRepositoryPort).findById(supplierId);
        verify(supplierRepositoryPort).findByName(command.getName());
        verify(supplierRepositoryPort).findByEmail(command.getEmail());
        verify(supplierRepositoryPort).findByPhone(command.getPhone());
        verify(supplierRepositoryPort).save(any(Supplier.class));
    }

    @Test
    void update_shouldReturnSupplierResponse_whenDetailsAreUnchanged() {
        SupplierUpdateCommand command = new SupplierUpdateCommand("Original Supplier", "original@example.com", "1234567890", "123 Main St", "Updated description");
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepositoryPort.save(any(Supplier.class))).thenReturn(Supplier.withId(supplierId, "Original Supplier", "original@example.com", "1234567890", "123 Main St", "Updated description", existingSupplier.getCreatedAt(), LocalDateTime.now()));

        SupplierResponse response = updateSupplierService.update(supplierId, command);

        assertNotNull(response);
        assertEquals("Original Supplier", response.getName());
        assertEquals("original@example.com", response.getEmail());
        assertEquals("1234567890", response.getPhone());
        verify(supplierRepositoryPort).findById(supplierId);
        verify(supplierRepositoryPort, never()).findByName(anyString());
        verify(supplierRepositoryPort, never()).findByEmail(anyString());
        verify(supplierRepositoryPort, never()).findByPhone(anyString());
        verify(supplierRepositoryPort).save(any(Supplier.class));
    }

    @Test
    void update_shouldThrowSupplierNotFoundException_whenSupplierDoesNotExist() {
        Long nonExistentId = 99L;
        SupplierUpdateCommand command = new SupplierUpdateCommand("New Supplier", "new@example.com", "0987654321", "456 Side Ave", "New description");
        when(supplierRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> updateSupplierService.update(nonExistentId, command));

        verify(supplierRepositoryPort).findById(nonExistentId);
        verify(supplierRepositoryPort, never()).findByName(anyString());
        verify(supplierRepositoryPort, never()).findByEmail(anyString());
        verify(supplierRepositoryPort, never()).findByPhone(anyString());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewNameAlreadyExists() {
        Supplier existingDuplicateSupplier = Supplier.withId(2L, "New Supplier", "other@example.com", "987", "street", "desc", LocalDateTime.now(), LocalDateTime.now());
        SupplierUpdateCommand command = new SupplierUpdateCommand("New Supplier", "new@example.com", "0987654321", "456 Side Ave", "New description");
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.of(existingDuplicateSupplier));

        assertThrows(DuplicateEntryException.class, () -> updateSupplierService.update(supplierId, command));

        verify(supplierRepositoryPort).findById(supplierId);
        verify(supplierRepositoryPort).findByName(command.getName());
        verify(supplierRepositoryPort, never()).findByEmail(anyString());
        verify(supplierRepositoryPort, never()).findByPhone(anyString());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewEmailAlreadyExists() {
        Supplier existingDuplicateSupplier = Supplier.withId(2L, "Another Supplier", "new@example.com", "987", "street", "desc", LocalDateTime.now(), LocalDateTime.now());
        SupplierUpdateCommand command = new SupplierUpdateCommand("New Supplier", "new@example.com", "0987654321", "456 Side Ave", "New description");
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.of(existingDuplicateSupplier));

        assertThrows(DuplicateEntryException.class, () -> updateSupplierService.update(supplierId, command));

        verify(supplierRepositoryPort).findById(supplierId);
        verify(supplierRepositoryPort).findByName(command.getName());
        verify(supplierRepositoryPort).findByEmail(command.getEmail());
        verify(supplierRepositoryPort, never()).findByPhone(anyString());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewPhoneAlreadyExists() {
        Supplier existingDuplicateSupplier = Supplier.withId(2L, "Another Supplier", "another@example.com", "0987654321", "street", "desc", LocalDateTime.now(), LocalDateTime.now());
        SupplierUpdateCommand command = new SupplierUpdateCommand("New Supplier", "new@example.com", "0987654321", "456 Side Ave", "New description");
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        when(supplierRepositoryPort.findByPhone(command.getPhone())).thenReturn(Optional.of(existingDuplicateSupplier));

        assertThrows(DuplicateEntryException.class, () -> updateSupplierService.update(supplierId, command));

        verify(supplierRepositoryPort).findById(supplierId);
        verify(supplierRepositoryPort).findByName(command.getName());
        verify(supplierRepositoryPort).findByEmail(command.getEmail());
        verify(supplierRepositoryPort).findByPhone(command.getPhone());
        verify(supplierRepositoryPort, never()).save(any(Supplier.class));
    }
}