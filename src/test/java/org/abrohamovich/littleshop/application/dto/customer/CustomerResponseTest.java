package org.abrohamovich.littleshop.application.dto.customer;

import org.abrohamovich.littleshop.domain.model.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnCustomerResponse() {
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "jphn.doe@example.com";
        String phone = "+1 50-598-1930";
        String address = "Somewhere in NY";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Customer customer = Customer.withId(id, firstName, lastName, email, phone, address, createdAt, updatedAt);

        CustomerResponse customerResponse = CustomerResponse.toResponse(customer);

        assertNotNull(customerResponse);
        assertEquals(customer.getId(), customerResponse.getId());
        assertEquals(firstName, customerResponse.getFirstName());
        assertEquals(lastName, customerResponse.getLastName());
        assertEquals(email, customerResponse.getEmail());
        assertEquals(phone, customerResponse.getPhone());
        assertEquals(address, customerResponse.getAddress());
        assertEquals(createdAt, customerResponse.getCreatedAt());
        assertEquals(updatedAt, customerResponse.getUpdatedAt());
    }

    @Test
    void toResponse_WithNullCategory_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CustomerResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("Customer cannot be null to continue the conversion."));
    }
}
