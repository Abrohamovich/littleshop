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
public class Category {
    private final Long id;
    private final LocalDateTime createdAt;
    private String name;
    private String description;
    private LocalDateTime updatedAt;

    private Category(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateSelf();
    }

    public static Category createNew(String name, String description) {
        return new Category(null, name, description, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Category withId(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for existing category");
        }
        return new Category(id, name, description, createdAt, updatedAt);
    }

    public void updateDetails(String name, String description) {
        this.name = name;
        this.description = description;
        this.updatedAt = LocalDateTime.now();

        validateSelf();
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("name cannot be null or empty");
        }
        if (description != null && description.isBlank()) {
            errors.add("description cannot be empty");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "Category validation failed: " + String.join(", ", errors);
            throw new CustomerValidationException(errorMessage);
        }
    }
}
