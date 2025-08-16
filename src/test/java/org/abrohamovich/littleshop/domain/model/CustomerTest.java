package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.customer.CustomerValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @AfterEach
    void tearDown() {
        id = null;
        firstName = null;
        lastName = null;
        email = null;
        phone = null;
        address = null;
        createdAt = null;
        updatedAt = null;
    }

//  createNewCustomer()

    @Test
    void createNewCustomer_WithValidData_ShouldReturnCustomer() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = "123 Main St";

        Customer customer = Customer.createNewCustomer(firstName, lastName, email, phone, address);

        assertNotNull(customer);
        assertNull(customer.getId());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(address, customer.getAddress());
        assertNotNull(customer.getCreatedAt());
        assertNotNull(customer.getUpdatedAt());
    }

    @Test
    void createNewCustomer_WithBlankFirstName_ShouldThrowException() {
        firstName = "   ";
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("first name cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithNullFirstName_ShouldThrowException() {
        firstName = null;
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("first name cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithBlankLastName_ShouldThrowException() {
        firstName = "John";
        lastName = "   ";
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("last name cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithNullLastName_ShouldThrowException() {
        firstName = "John";
        lastName = null;
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("last name cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithBlankEmail_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "   ";
        phone = "123-456-7890";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithNullEmail_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = null;
        phone = "123-456-7890";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithBlankPhone_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = "   ";
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithNullPhone_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = null;
        address = "123 Main St";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void createNewCustomer_WithBlankAddress_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = "   ";

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.createNewCustomer(firstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("address cannot be empty"));
    }

    @Test
    void createNewCustomer_WithNullAddress_ShouldReturnCustomer() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        phone = "123-456-7890";
        address = null;

        Customer customer = Customer.createNewCustomer(firstName, lastName, email, phone, address);

        assertNotNull(customer);
        assertNull(customer.getId());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertNull(customer.getAddress());
        assertNotNull(customer.getCreatedAt());
        assertNotNull(customer.getUpdatedAt());
    }

//  withId()

    @Test
    void withId_WithValidData_ShouldReturnCustomer() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals(id, customer.getId());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(address, customer.getAddress());
        assertEquals(createdAt, customer.getCreatedAt());
        assertEquals(updatedAt, customer.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("ID cannot be null for existing customer"));
    }

    @Test
    void withId_WithBlankFirstName_ShouldThrowException() {
        id = 1L;
        firstName = "   ";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("first name cannot be null or empty"));
    }

    @Test
    void withId_WithNullFirstName_ShouldThrowException() {
        id = 1L;
        firstName = null;
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("first name cannot be null or empty"));
    }

    @Test
    void withId_WithBlankLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "   ";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("last name cannot be null or empty"));
    }

    @Test
    void withId_WithNullLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = null;
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("last name cannot be null or empty"));
    }

    @Test
    void withId_WithBlankEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "   ";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void withId_WithNullEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = null;
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void withId_WithBlankPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "   ";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void withId_WithNullPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = null;
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void withId_WithBlankAddress_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "   ";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("address cannot be empty"));
    }

//  updateDetails()

    @Test
    void updateDetails_WithValidData_ShouldUpdateCustomer() {
        id = 1L;
        firstName = "Jane";
        String newFirstName = "Janet";
        lastName = "Smith";
        String newLastName = "Jones";
        email = "jane.smith@example.com";
        String newEmail = "janet.jones@example.com";
        phone = "098-765-4321";
        String newPhone = "000-000-0000";
        address = "456 Oak Ave";
        String newAddress = "789 Pine Rd";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        customer.updateDetails(newFirstName, newLastName, newEmail, newPhone, newAddress);

        assertNotNull(customer);
        assertEquals(id, customer.getId());
        assertEquals(newFirstName, customer.getFirstName());
        assertEquals(newLastName, customer.getLastName());
        assertEquals(newEmail, customer.getEmail());
        assertEquals(newPhone, customer.getPhone());
        assertEquals(newAddress, customer.getAddress());
        assertEquals(createdAt, customer.getCreatedAt());
        assertNotEquals(updatedAt, customer.getUpdatedAt());
    }

    @Test
    void updateDetails_WithBlankFirstName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        String newFirstName = "   ";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(newFirstName, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("first name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullFirstName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(null, lastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("first name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        String newLastName = "   ";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, newLastName, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("last name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, null, email, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("last name cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        String newEmail = "   ";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, lastName, newEmail, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, lastName, null, phone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        String newPhone = "   ";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, lastName, email, newPhone, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, lastName, email, null, address);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankAddress_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        phone = "098-765-4321";
        address = "456 Oak Ave";
        String newAddress = "   ";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerValidationException ex = assertThrows(CustomerValidationException.class, () -> {
            customer.updateDetails(firstName, lastName, email, phone, newAddress);
        });

        assertTrue(ex.getMessage().startsWith("Customer validation failed:"));
        assertTrue(ex.getMessage().contains("address cannot be empty"));
    }
}