package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Customer {
    private final Long id;
    private final LocalDateTime createdAt;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime updatedAt;

    private Customer(Long id, String firstName, String lastName, String email, String phone, String address,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateSelf();
    }

    public static Customer createNewCustomer(String firstName, String lastName, String email, String phone,
                                             String address) {
        return new Customer(null, firstName, lastName, email, phone, address, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Customer withId(Long id, String firstName, String lastName, String email, String phone,
                                  String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for existing customer");
        }
        return new Customer(id, firstName, lastName, email, phone, address, createdAt, updatedAt);
    }

    public void updateDetails(String firstName, String lastName, String email, String phone,
                              String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.updatedAt = LocalDateTime.now();

        validateSelf();
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (firstName == null || firstName.isBlank()) {
            errors.add("first name cannot be null or empty");
        }
        if (lastName == null || lastName.isBlank()) {
            errors.add("last name cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            errors.add("email cannot be null or empty");
        }
        if (phone == null || phone.isBlank()) {
            errors.add("phone cannot be null or empty");
        }
        if (address != null && address.isBlank()) {
            errors.add("address cannot be empty");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "Customer validation failed: " + String.join(", ", errors) + ".";
            throw new CustomerValidationException(errorMessage);
        }
    }
}
