package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.abrohamovich.littleshop.domain.exception.offer.OfferValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Offer {
    private final Long id;
    private final LocalDateTime createdAt;
    private String name;
    private double price;
    private OfferType type;
    private String description;
    private Category category;
    private Supplier supplier;
    private LocalDateTime updatedAt;

    private Offer(Long id, String name, double price, OfferType type, String description, Category category,
                  Supplier supplier, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateSelf();
    }

    private Offer(Long id, String name, double price, OfferType type, String description,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Offer createNew(String name, double price, OfferType type,
                                  String description, Category category, Supplier supplier) {
        return new Offer(null, name, price, type, description, category, supplier, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Offer withId(Long id, String name, double price, OfferType type, String description, Category category,
                               Supplier supplier, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for existing Offer");
        }
        return new Offer(id, name, price, type, description, category, supplier, createdAt, updatedAt);
    }

    public static Offer createForPersistenceHydration(Long id, String name, double price, OfferType type,
                                                      String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Offer(id, name, price, type, description, createdAt, updatedAt);
    }

    public void updateDetails(String name, double price, OfferType type, String description,
                              Category category, Supplier supplier) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.updatedAt = LocalDateTime.now();

        validateSelf();
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("name cannot be null or empty");
        }
        if (price <= 0) {
            errors.add("price must be positive");
        }
        if (type == null) {
            errors.add("type cannot be null");
        }
        if (description != null && description.isBlank()) {
            errors.add("description cannot be empty (if provided)");
        }
        if (category == null) {
            errors.add("category cannot be null");
        }
        if (supplier == null) {
            errors.add("supplier cannot be null");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "Offer validation failed: " + String.join(", ", errors) + ".";
            throw new OfferValidationException(errorMessage);
        }
    }
}
