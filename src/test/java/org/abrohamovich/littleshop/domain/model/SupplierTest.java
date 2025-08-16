package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.supplier.SupplierValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @AfterEach
    void tearDown() {
        id = null;
        name = null;
        email = null;
        phone = null;
        address = null;
        description = null;
        createdAt = null;
        updatedAt = null;
    }

//  createNew()

    @Test
    void createNew_WithValidData_ShouldReturnSupplier() {
        name = "Global Supplies Inc.";
        email = "contact@globalsupplies.com";
        phone = "111-222-3333";
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        Supplier supplier = Supplier.createNew(name, email, phone, address, description);

        assertNotNull(supplier);
        assertNull(supplier.getId());
        assertEquals(name, supplier.getName());
        assertEquals(email, supplier.getEmail());
        assertEquals(phone, supplier.getPhone());
        assertEquals(address, supplier.getAddress());
        assertEquals(description, supplier.getDescription());
        assertNotNull(supplier.getCreatedAt());
        assertNotNull(supplier.getUpdatedAt());
    }

    @Test
    void createNew_WithNullName_ShouldThrowException() {
        name = null;
        email = "contact@globalsupplies.com";
        phone = "111-222-3333";
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void createNew_WithBlankName_ShouldThrowException() {
        name = "   ";
        email = "contact@globalsupplies.com";
        phone = "111-222-3333";
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void createNew_WithNullEmail_ShouldThrowException() {
        name = "Global Supplies Inc.";
        email = null;
        phone = "111-222-3333";
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void createNew_WithBlankEmail_ShouldThrowException() {
        name = "Global Supplies Inc.";
        email = "   ";
        phone = "111-222-3333";
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void createNew_WithNullPhone_ShouldThrowException() {
        name = "Global Supplies Inc.";
        email = "contact@globalsupplies.com";
        phone = null;
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void createNew_WithBlankPhone_ShouldThrowException() {
        name = "Global Supplies Inc.";
        email = "contact@globalsupplies.com";
        phone = "   ";
        address = "123 Supply Lane";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void createNew_WithBlankAddress_ShouldThrowException() {
        name = "Global Supplies Inc.";
        email = "contact@globalsupplies.com";
        phone = "111-222-3333";
        address = "   ";
        description = "A global supplier of various goods.";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("address cannot be empty (if provided)"));
    }

    @Test
    void createNew_WithBlankDescription_ShouldThrowException() {
        name = "Global Supplies Inc.";
        email = "contact@globalsupplies.com";
        phone = "111-222-3333";
        address = "123 Supply Lane";
        description = "   ";

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.createNew(name, email, phone, address, description);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot be empty (if provided)"));
    }

//  withId()

    @Test
    void withId_WithValidData_ShouldReturnSupplier() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = "info@techdist.com";
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        assertNotNull(supplier);
        assertEquals(id, supplier.getId());
        assertEquals(name, supplier.getName());
        assertEquals(email, supplier.getEmail());
        assertEquals(phone, supplier.getPhone());
        assertEquals(address, supplier.getAddress());
        assertEquals(description, supplier.getDescription());
        assertEquals(createdAt, supplier.getCreatedAt());
        assertEquals(updatedAt, supplier.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        id = null;
        name = "Tech Distributors Co.";
        email = "info@techdist.com";
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        NullPointerException ex = assertThrows(NullPointerException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("ID cannot be null for existing supplier"));
    }

    @Test
    void withId_WithNullName_ShouldThrowException() {
        id = 1L;
        name = null;
        email = "info@techdist.com";
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void withId_WithBlankName_ShouldThrowException() {
        id = 1L;
        name = "   ";
        email = "info@techdist.com";
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void withId_WithNullEmail_ShouldThrowException() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = null;
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void withId_WithBlankEmail_ShouldThrowException() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = "   ";
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void withId_WithNullPhone_ShouldThrowException() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = "info@techdist.com";
        phone = null;
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void withId_WithBlankPhone_ShouldThrowException() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = "info@techdist.com";
        phone = "   ";
        address = "456 Tech Blvd";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void withId_WithBlankAddress_ShouldThrowException() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = "info@techdist.com";
        phone = "444-555-6666";
        address = "   ";
        description = "Distributes electronic components.";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("address cannot be empty (if provided)"));
    }

    @Test
    void withId_WithBlankDescription_ShouldThrowException() {
        id = 1L;
        name = "Tech Distributors Co.";
        email = "info@techdist.com";
        phone = "444-555-6666";
        address = "456 Tech Blvd";
        description = "   ";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot be empty (if provided)"));
    }

//  updateDetails()

    @Test
    void updateDetails_WithValidData_ShouldUpdateSupplier() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);

        assertNotNull(supplier);
        assertEquals(id, supplier.getId());
        assertEquals(newName, supplier.getName());
        assertEquals(newEmail, supplier.getEmail());
        assertEquals(newPhone, supplier.getPhone());
        assertEquals(newAddress, supplier.getAddress());
        assertEquals(newDescription, supplier.getDescription());
        assertEquals(createdAt, supplier.getCreatedAt());
        assertNotEquals(updatedAt, supplier.getUpdatedAt());
    }

    @Test
    void updateDetails_WithNullName_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = null;
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankName_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "   ";
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullEmail_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = null;
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankEmail_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = "   ";
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullPhone_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = null;
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankPhone_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = "   ";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankAddress_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "   ";
        description = "Old Description";
        String newDescription = "New Description";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("address cannot be empty (if provided)"));
    }

    @Test
    void updateDetails_WithBlankDescription_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        email = "old@email.com";
        String newEmail = "new@email.com";
        phone = "111-111-1111";
        String newPhone = "222-222-2222";
        address = "Old Address";
        String newAddress = "New Address";
        description = "Old Description";
        String newDescription = "   ";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Supplier supplier = Supplier.withId(id, name, email, phone, address, description, createdAt, updatedAt);

        SupplierValidationException ex = assertThrows(SupplierValidationException.class, () -> {
            supplier.updateDetails(newName, newEmail, newPhone, newAddress, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Supplier validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot be empty (if provided)"));
    }
}
