package org.abrohamovich.littleshop.application.usecase.supplier;

import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
import org.abrohamovich.littleshop.domain.model.Supplier;
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
public class DeleteSupplierServiceTest {

    @Mock
    private SupplierRepositoryPort supplierRepositoryPort;

    @InjectMocks
    private DeleteSupplierService deleteSupplierService;

    @Test
    void deleteById_shouldDeleteSupplier_whenSupplierExists() {
        Long supplierId = 1L;
        Supplier existingSupplier = Supplier.withId(supplierId, "Test Supplier", "test@mail.com", "123", "street", "desc", LocalDateTime.now(), LocalDateTime.now());

        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(existingSupplier));

        deleteSupplierService.deleteById(supplierId);

        verify(supplierRepositoryPort, times(1)).findById(supplierId);
        verify(supplierRepositoryPort, times(1)).deleteById(supplierId);
    }

    @Test
    void deleteById_shouldThrowException_whenSupplierNotFound() {
        Long nonExistentId = 99L;

        when(supplierRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> deleteSupplierService.deleteById(nonExistentId));

        verify(supplierRepositoryPort, times(1)).findById(nonExistentId);
        verify(supplierRepositoryPort, never()).deleteById(anyLong());
    }
}