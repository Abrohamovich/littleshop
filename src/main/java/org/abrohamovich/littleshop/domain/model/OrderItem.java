package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.abrohamovich.littleshop.domain.exception.orderItem.OrderItemValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class OrderItem {
    private final Long id;
    private Offer offer;
    private final double priceAtTimeOfOrder;
    private final LocalDateTime createdAt;
    private int quantity;
    private LocalDateTime updatedAt;

    private OrderItem(Long id, Offer offer, int quantity, double priceAtTimeOfOrder,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.offer = offer;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validateSelf();
    }

    private OrderItem(Long id, int quantity, double priceAtTimeOfOrder,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderItem createNew(Offer offer, int quantity) {
        if (offer == null) {
            throw new IllegalArgumentException("Offer cannot be null when creating new OrderItem.");
        }
        return new OrderItem(null, offer, quantity, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static OrderItem withId(Long id, Offer offer, int quantity, double priceAtTimeOfOrder,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for existing OrderItem when calling withId");
        }
        return new OrderItem(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
    }

    public static OrderItem createForPersistenceHydration(Long id, int quantity, double priceAtTimeOfOrder,
                                                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new OrderItem(id, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.updatedAt = LocalDateTime.now();

        validateSelf();
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (offer == null) {
            errors.add("offer cannot be null");
        }
        if (quantity <= 0) {
            errors.add("quantity cannot be negative or zero");
        }
        if (priceAtTimeOfOrder <= 0) {
            errors.add("priceAtTimeOfOrder cannot be negative or zero");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "OrderItem validation failed: " + String.join(", ", errors) + ".";
            throw new OrderItemValidationException(errorMessage);
        }
    }
}
