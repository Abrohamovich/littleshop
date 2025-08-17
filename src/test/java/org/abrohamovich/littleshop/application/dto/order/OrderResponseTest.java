package org.abrohamovich.littleshop.application.dto.order;

import org.abrohamovich.littleshop.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnOrderResponse() {
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Supplier supplier = Supplier.withId(1L, "name", "email", "phone",
                "address", "description", createdAt, updatedAt);
        Category category = Category.withId(1L, "name", "description", createdAt, updatedAt);
        Customer customer = Customer.withId(1L, "firstName", "lastName", "email", "phone", "address", createdAt, updatedAt);
        User user = User.withId(1L, "firstName", "lastName", "email", "password", UserRole.WORKER, "phone", createdAt, updatedAt);
        Offer offer = Offer.withId(1L, "name", 4.4, OfferType.PRODUCT, "description",
                category, supplier, createdAt, updatedAt);
        int quantity = 3;
        double priceAtTimeOfOrder = 5.4;
        OrderItem orderItem = OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        OrderStatus status = OrderStatus.IN_PROGRESS;

        Order order = Order.withId(id, customer, user, status, List.of(orderItem), createdAt, updatedAt);

        OrderResponse orderResponse = OrderResponse.toResponse(order);

        assertNotNull(orderResponse);
        assertEquals(order.getId(), orderResponse.getId());
        assertNotNull(orderResponse.getCustomer());
        assertNotNull(orderResponse.getUser());
        assertEquals(order.getCreatedAt(), orderResponse.getCreatedAt());
        assertEquals(order.getUpdatedAt(), orderResponse.getUpdatedAt());
    }

    @Test
    void toResponse_WithNullOrder_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> OrderResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("Order cannot be null to continue the conversion."));
    }
}
