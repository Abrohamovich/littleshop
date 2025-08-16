package org.abrohamovich.littleshop.application.dto.orderItem;

import org.abrohamovich.littleshop.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class OrderItemResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnOrderItemResponse() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Supplier supplier = Supplier.withId(1L, "name", "email", "phone",
                "address", "description", createdAt, updatedAt);
        Category category = Category.withId(1L, "name", "description", createdAt, updatedAt);
        Long id = 1L;
        Offer offer = Offer.withId(1L, "name", 4.4, OfferType.PRODUCT, "description",
                category, supplier, createdAt, updatedAt);
        int quantity = 3;
        double priceAtTimeOfOrder = 5.4;


        OrderItem orderItem = OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);

        OrderItemResponse orderItemResponse = OrderItemResponse.toResponse(orderItem);

        assertNotNull(orderItemResponse);
        assertEquals(orderItem.getId(), orderItemResponse.getId());
        assertNotNull(orderItemResponse.getOffer());
        assertEquals(orderItem.getQuantity(), orderItemResponse.getQuantity());
        assertEquals(orderItem.getPriceAtTimeOfOrder(), orderItemResponse.getPriceAtTimeOfOrder());
        assertEquals(orderItem.getCreatedAt(), orderItemResponse.getCreatedAt());
        assertEquals(orderItem.getUpdatedAt(), orderItemResponse.getUpdatedAt());
    }

    @Test
    void toResponse_WithNullOrderItem_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> OrderItemResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("OrderItem cannot be null to continue the conversion."));
    }
}
