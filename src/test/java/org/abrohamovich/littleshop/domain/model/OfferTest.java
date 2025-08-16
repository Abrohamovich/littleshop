package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.offer.OfferValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {
    private Long id;
    private String name;
    private double price;
    private OfferType type;
    private String description;
    private Category category;
    private Supplier supplier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @AfterEach
    void tearDown() {
        id = null;
        name = null;
        price = 0;
        type = null;
        description = null;
        category = null;
        supplier = null;
        createdAt = null;
        updatedAt = null;
    }

    // createNew()

    @Test
    void createNew_WithValidData_ShouldReturnOffer() {
        name = "Smartphone";
        price = 999.99;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        Offer offer = Offer.createNew(name, price, type, description, category, supplier);

        assertNotNull(offer);
        assertNull(offer.getId());
        assertEquals(name, offer.getName());
        assertEquals(price, offer.getPrice());
        assertEquals(type, offer.getType());
        assertEquals(description, offer.getDescription());
        assertEquals(category, offer.getCategory());
        assertEquals(supplier, offer.getSupplier());
        assertNotNull(offer.getCreatedAt());
        assertNotNull(offer.getUpdatedAt());
    }

    @Test
    void createNew_WithNullName_ShouldThrowException() {
        name = null;
        price = 999.99;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void createNew_WithBlankName_ShouldThrowException() {
        name = "   ";
        price = 999.99;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void createNew_WithZeroPrice_ShouldThrowException() {
        name = "Smartphone";
        price = 0;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("price must be positive"));
    }

    @Test
    void createNew_WithNegativePrice_ShouldThrowException() {
        name = "Smartphone";
        price = -10.0;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("price must be positive"));
    }

    @Test
    void createNew_WithNullType_ShouldThrowException() {
        name = "Smartphone";
        price = 999.99;
        type = null;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("type cannot be null"));
    }

    @Test
    void createNew_WithBlankDescription_ShouldThrowException() {
        name = "Smartphone";
        price = 999.99;
        type = OfferType.PRODUCT;
        description = "   ";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot be empty (if provided)"));
    }

    @Test
    void createNew_WithNullCategory_ShouldThrowException() {
        name = "Smartphone";
        price = 999.99;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = null;
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("category cannot be null"));
    }

    @Test
    void createNew_WithNullSupplier_ShouldThrowException() {
        name = "Smartphone";
        price = 999.99;
        type = OfferType.PRODUCT;
        description = "A new smartphone model.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = null;

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.createNew(name, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("supplier cannot be null"));
    }

    // withId()

    @Test
    void withId_WithValidData_ShouldReturnOffer() {
        id = 1L;
        name = "Laptop";
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        assertNotNull(offer);
        assertEquals(id, offer.getId());
        assertEquals(name, offer.getName());
        assertEquals(price, offer.getPrice());
        assertEquals(type, offer.getType());
        assertEquals(description, offer.getDescription());
        assertEquals(category, offer.getCategory());
        assertEquals(supplier, offer.getSupplier());
        assertEquals(createdAt, offer.getCreatedAt());
        assertEquals(updatedAt, offer.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        id = null;
        name = "Laptop";
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("ID cannot be null for existing Offer"));
    }

    @Test
    void withId_WithNullName_ShouldThrowException() {
        id = 1L;
        name = null;
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void withId_WithBlankName_ShouldThrowException() {
        id = 1L;
        name = "   ";
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void withId_WithZeroPrice_ShouldThrowException() {
        id = 1L;
        name = "Laptop";
        price = 0;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("price must be positive"));
    }

    @Test
    void withId_WithNegativePrice_ShouldThrowException() {
        id = 1L;
        name = "Laptop";
        price = -10.0;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("price must be positive"));
    }

    @Test
    void withId_WithNullType_ShouldThrowException() {
        id = 1L;
        name = "Laptop";
        price = 1499.99;
        type = null;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("type cannot be null"));
    }

    @Test
    void withId_WithBlankDescription_ShouldThrowException() {
        id = 1L;
        name = "Laptop";
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "   ";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot be empty (if provided)"));
    }

    @Test
    void withId_WithNullCategory_ShouldThrowException() {
        id = 1L;
        name = "Laptop";
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = null;
        supplier = Supplier.withId(1L, "TechCorp", "info@techcorp.com", "123-456-7890", "123 Tech St", "Supplier of tech goods", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("category cannot be null"));
    }

    @Test
    void withId_WithNullSupplier_ShouldThrowException() {
        id = 1L;
        name = "Laptop";
        price = 1499.99;
        type = OfferType.PRODUCT;
        description = "High-performance laptop.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = null;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("supplier cannot be null"));
    }

    // createForPersistenceHydration()

    @Test
    void createForPersistenceHydration_WithValidData_ShouldReturnOffer() {
        id = 1L;
        name = "Smartwatch";
        price = 299.99;
        type = OfferType.PRODUCT;
        description = "A stylish smartwatch.";
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.createForPersistenceHydration(id, name, price, type, description, createdAt, updatedAt);

        assertNotNull(offer);
        assertEquals(id, offer.getId());
        assertEquals(name, offer.getName());
        assertEquals(price, offer.getPrice());
        assertEquals(type, offer.getType());
        assertEquals(description, offer.getDescription());
        assertEquals(createdAt, offer.getCreatedAt());
        assertEquals(updatedAt, offer.getUpdatedAt());
        assertNull(offer.getCategory());
        assertNull(offer.getSupplier());
    }

    // updateDetails()

    @Test
    void updateDetails_WithValidData_ShouldUpdateOffer() {
        id = 1L;
        name = "Old Name";
        String newName = "New Name";
        price = 100.0;
        double newPrice = 200.0;
        type = OfferType.PRODUCT;
        OfferType newType = OfferType.SERVICE;
        description = "Old description.";
        String newDescription = "New description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        Category newCategory = Category.withId(2L, "Services", "Digital", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        Supplier newSupplier = Supplier.withId(2L, "Supplier B", "b@b.com", "222", "addr B", "desc B", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        offer.updateDetails(newName, newPrice, newType, newDescription, newCategory, newSupplier);

        assertNotNull(offer);
        assertEquals(id, offer.getId());
        assertEquals(newName, offer.getName());
        assertEquals(newPrice, offer.getPrice());
        assertEquals(newType, offer.getType());
        assertEquals(newDescription, offer.getDescription());
        assertEquals(newCategory, offer.getCategory());
        assertEquals(newSupplier, offer.getSupplier());
        assertEquals(createdAt, offer.getCreatedAt());
        assertNotEquals(updatedAt, offer.getUpdatedAt());
    }

    @Test
    void updateDetails_WithNullName_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(null, price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankName_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails("   ", price, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithZeroPrice_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(name, 0, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("price must be positive"));
    }

    @Test
    void updateDetails_WithNegativePrice_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(name, -10.0, type, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("price must be positive"));
    }

    @Test
    void updateDetails_WithNullType_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(name, price, null, description, category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("type cannot be null"));
    }

    @Test
    void updateDetails_WithBlankDescription_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(name, price, type, "   ", category, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot be empty (if provided)"));
    }

    @Test
    void updateDetails_WithNullCategory_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(name, price, type, description, null, supplier);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("category cannot be null"));
    }

    @Test
    void updateDetails_WithNullSupplier_ShouldThrowException() {
        id = 1L;
        name = "Old Name";
        price = 100.0;
        type = OfferType.PRODUCT;
        description = "Old description.";
        category = Category.withId(1L, "Electronics", "Gadgets", LocalDateTime.now(), LocalDateTime.now());
        supplier = Supplier.withId(1L, "Supplier A", "a@a.com", "111", "addr A", "desc A", LocalDateTime.now(), LocalDateTime.now());
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Offer offer = Offer.withId(id, name, price, type, description, category, supplier, createdAt, updatedAt);

        OfferValidationException ex = assertThrows(OfferValidationException.class, () -> {
            offer.updateDetails(name, price, type, description, category, null);
        });

        assertTrue(ex.getMessage().startsWith("Offer validation failed:"));
        assertTrue(ex.getMessage().contains("supplier cannot be null"));
    }
}
