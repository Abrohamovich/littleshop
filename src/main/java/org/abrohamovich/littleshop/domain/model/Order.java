package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.abrohamovich.littleshop.domain.exception.order.OrderValidationException;
import org.abrohamovich.littleshop.domain.exception.orderItem.OrderItemNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
public class Order {
    private final Long id;
    private List<OrderItem> items;
    private final LocalDateTime createdAt;
    private Customer customer;
    private User user;
    private OrderStatus status;
    private LocalDateTime updatedAt;

    private Order(Long id, Customer customer, User user, OrderStatus status,
                 List<OrderItem> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.status = status;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateSelf();
    }

    private Order(Long id, LocalDateTime createdAt, OrderStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public static Order createNew(Customer customer, User user, List<OrderItem> items) {
        OrderStatus initialStatus = OrderStatus.IN_PROGRESS;
        return new Order(null, customer, user, initialStatus, items, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Order withId(Long id, Customer customer, User user, OrderStatus status,
                               List<OrderItem> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for existing Order");
        }
        return new Order(id, customer, user, status, items, createdAt, updatedAt);
    }

    public static Order createForPersistenceHydration(Long id, LocalDateTime createdAt,
                                                      OrderStatus status, LocalDateTime updatedAt) {
        return new Order(id, createdAt, status, updatedAt);
    }

    public void updateDetails(Customer customer) {
        this.customer = customer;
        this.updatedAt = LocalDateTime.now();

        validateSelf();
    }

    public void addOrderItem(Offer offer, int quantity) {
        OrderItem newItem = OrderItem.createNew(offer, quantity);
        this.items.add(newItem);
        this.updatedAt = LocalDateTime.now();
        validateSelf();
    }

    public void updateOrderItemQuantity(Long orderItemId, int newQuantity) {
        OrderItem itemToUpdate = this.items.stream()
                .filter(item -> Objects.equals(item.getId(), orderItemId))
                .findFirst()
                .orElseThrow(() -> new OrderItemNotFoundException("Order item with ID " + orderItemId + " not found in this order."));

        itemToUpdate.updateQuantity(newQuantity);
        this.updatedAt = LocalDateTime.now();
        validateSelf();
    }

    public void removeOrderItem(Long orderItemId) {
        boolean removed = this.items.removeIf(item -> Objects.equals(item.getId(), orderItemId));
        if (!removed) {
            throw new OrderItemNotFoundException("Order item with ID " + orderItemId + " not found in this order to remove.");
        }
        this.updatedAt = LocalDateTime.now();
        validateSelf();
    }

    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("New order status cannot be null.");
        }
        if (this.status == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new OrderValidationException("Cannot change status from CANCELLED.");
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public double totalPrice() {
        return items.stream().mapToDouble(item -> item.getQuantity() * item.getPriceAtTimeOfOrder()).sum();
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (customer == null) {
            errors.add("customer cannot be null");
        }
        if (user == null) {
            errors.add("user cannot be null");
        }
        if (status == null) {
            errors.add("status cannot be null");
        }
        if (items == null || items.isEmpty()) {
            errors.add("order must contain at least one item");
        } else {
            List<String> itemValidationErrors = items.stream()
                    .filter(Objects::isNull)
                    .map(item -> "order item cannot be null")
                    .toList();
            if (!itemValidationErrors.isEmpty()) {
                errors.addAll(itemValidationErrors);
            }
        }

        if (!errors.isEmpty()) {
            String errorMessage = "Order validation failed: " + String.join(", ", errors) + ".";
            throw new OrderValidationException(errorMessage);
        }
    }
}
