package org.abrohamovich.littleshop.application.dto.supplier;

import org.abrohamovich.littleshop.domain.model.Supplier;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnSupplierResponse() {
        Long id = 1L;
        String name = "Name";
        String email = "Email";
        String phone = "+1 50 402-14-43";
        String address = "Address";
        String description = "Description";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Supplier supplier = Supplier.withId(id, name, email, phone,
                address, description, createdAt, updatedAt);

        SupplierResponse supplierResponse = SupplierResponse.toResponse(supplier);

        assertNotNull(supplierResponse);
        assertEquals(supplier.getId(), supplierResponse.getId());
        assertEquals(supplier.getName(), supplierResponse.getName());
        assertEquals(supplier.getEmail(), supplierResponse.getEmail());
        assertEquals(supplier.getPhone(), supplierResponse.getPhone());
        assertEquals(supplier.getAddress(), supplierResponse.getAddress());
        assertEquals(supplier.getDescription(), supplierResponse.getDescription());
        assertEquals(supplier.getCreatedAt(), supplierResponse.getCreatedAt());
        assertEquals(supplier.getUpdatedAt(), supplierResponse.getUpdatedAt());
    }

    @Test
    void toResponse_WithNullSupplier_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> SupplierResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("Supplier cannot be null to continue the conversion."));
    }
}
