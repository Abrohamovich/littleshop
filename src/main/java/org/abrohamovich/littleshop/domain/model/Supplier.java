package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Supplier {
    private final Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Supplier(Long id, String name, String email, String phone, String address,
                     String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateSelf();
    }

    public static Supplier createNew(String name, String email, String phone, String address, String description) {
        return new Supplier(null, name, email, phone, address, description, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Supplier withId(Long id, String name, String email, String phone, String address,
                                  String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new NullPointerException("ID cannot be null for existing supplier");
        }
        return new Supplier(id, name, email, phone, address, description, createdAt, updatedAt);
    }

    public void updateDetails(String name, String email, String phone, String address, String description) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.updatedAt = LocalDateTime.now();

        validateSelf();
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("name cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            errors.add("email cannot be null or empty");
        }
        if (phone == null || phone.isBlank()) {
            errors.add("phone cannot be null or empty");
        }
        if (address != null && address.isBlank()) {
            errors.add("address cannot be empty (if provided)");
        }
        if (description != null && description.isBlank()) {
            errors.add("description cannot be empty (if provided)");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "Supplier validation failed: " + String.join(", ", errors);
            throw new SupplierValidationException(errorMessage);
        }
    }
}
