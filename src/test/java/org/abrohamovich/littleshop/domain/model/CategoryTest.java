package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.customer.CustomerValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @AfterEach
    void tearDown() {
        id = null;
        name = null;
        description = null;
        createdAt = null;
        updatedAt = null;
    }

//  createNew()

    @Test
    void createNew_WithValidData_ShouldReturnCategory() {
        name = "Electronics";
        description = "Gadgets and devices";

        Category category = Category.createNew(name, description);

        assertNotNull(category);
        assertNull(category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
    }

    @Test
    void createNew_WithBlankName_ShouldThrowException() {
        name = "  ";
        description = "Gadgets and devices";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Category.createNew(name, description);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void createNew_WithNullName_ShouldThrowException() {
        description = "Gadgets and devices";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Category.createNew(name, description);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void createNew_WithBlankDescription_ShouldThrowException() {
        name = "Electronics";
        description = "   ";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Category.createNew(name, description);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot empty if present"));
    }

//  withId()

    @Test
    void withId_WithValidData_ShouldReturnCategory() {
        id = 2L;
        name = "Electronics";
        description = "Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        Category category = Category.withId(id, name, description, createdAt, updatedAt);

        assertNotNull(category);
        assertNotNull(category.getId());
        assertNotNull(category.getName());
        assertNotNull(category.getDescription());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
        assertEquals(createdAt, category.getCreatedAt());
        assertEquals(updatedAt, category.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        name = "Electronics";
        description = "Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Category.withId(id, name, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("ID cannot be null for existing category."));
    }

    @Test
    void withId_WithBlankName_ShouldThrowException() {
        id = 2L;
        name = "   ";
        description = "Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Category.withId(id, name, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void withId_WithNullName_ShouldThrowException() {
        id = 2L;
        description = "Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Category.withId(id, name, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void withId_WithBlankDescription_ShouldThrowException() {
        id = 2L;
        name = "Electronics";
        description = "   ";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Category.withId(id, name, description, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot empty if present"));
    }

//  updateDetails()

    @Test
    void updateDetails_WithValidData_ShouldUpdateCategory() {
        id = 2L;
        name = "Electronics";
        String newName = "New Electronics";
        description = "Gadgets and devices";
        String newDescription = "New Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        Category category = Category.withId(id, name, description, createdAt, updatedAt);

        category.updateDetails(newName, newDescription);

        assertNotNull(category);
        assertNotNull(category.getId());
        assertNotNull(category.getName());
        assertNotNull(category.getDescription());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNotEquals(name, category.getName());
        assertNotEquals(description, category.getDescription());
    }

    @Test
    void updateDetails_WithBlankName_ShouldThrowException() {
        id = 2L;
        name = "Electronics";
        String newName = "   ";
        description = "Gadgets and devices";
        String newDescription = "New Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        Category category = Category.withId(id, name, description, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            category.updateDetails(newName, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullName_ShouldThrowException() {
        id = 2L;
        name = "Electronics";
        description = "Gadgets and devices";
        String newDescription = "New Gadgets and devices";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        Category category = Category.withId(id, name, description, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            category.updateDetails(null, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankDescription_ShouldThrowException() {
        id = 2L;
        name = "Electronics";
        String newName = "New Electronics";
        description = "Gadgets and devices";
        String newDescription = "   ";
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        Category category = Category.withId(id, name, description, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            category.updateDetails(newName, newDescription);
        });

        assertTrue(ex.getMessage().startsWith("Category validation failed:"));
        assertTrue(ex.getMessage().contains("description cannot empty if present"));
    }

}