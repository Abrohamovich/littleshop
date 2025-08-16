package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.orderItem.OrderItemValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private Long id;
    private Offer offer;
    private double priceAtTimeOfOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int quantity;

    @BeforeEach
    void setUp() {
        offer = Offer.withId(1L, "Test Offer", 10.0, OfferType.PRODUCT, "A test product",
                Category.withId(1L, "Category", "Description", LocalDateTime.now(), LocalDateTime.now()),
                Supplier.withId(1L, "Supplier", "test@test.com", "123", "address", "desc", LocalDateTime.now(), LocalDateTime.now()),
                LocalDateTime.now(), LocalDateTime.now());
    }

    @AfterEach
    void tearDown() {
        id = null;
        offer = null;
        priceAtTimeOfOrder = 0;
        createdAt = null;
        updatedAt = null;
        quantity = 0;
    }

//  createNew()

    @Test
    void createNew_WithValidData_ShouldReturnOrderItem() {
        quantity = 5;

        OrderItem orderItem = OrderItem.createNew(offer, quantity);

        assertNotNull(orderItem);
        assertNull(orderItem.getId());
        assertEquals(offer, orderItem.getOffer());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(offer.getPrice(), orderItem.getPriceAtTimeOfOrder());
        assertNotNull(orderItem.getCreatedAt());
        assertNotNull(orderItem.getUpdatedAt());
    }

    @Test
    void createNew_WithNullOffer_ShouldThrowException() {
        quantity = 5;

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            OrderItem.createNew(null, quantity);
        });

        assertTrue(ex.getMessage().startsWith("Offer cannot be null when creating new OrderItem."));
    }

    @Test
    void createNew_WithZeroQuantity_ShouldThrowException() {
        quantity = 0;

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.createNew(offer, quantity);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

    @Test
    void createNew_WithNegativeQuantity_ShouldThrowException() {
        quantity = -1;

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.createNew(offer, quantity);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

//  withId()

    @Test
    void withId_WithValidData_ShouldReturnOrderItem() {
        id = 1L;
        quantity = 3;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItem orderItem = OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);

        assertNotNull(orderItem);
        assertEquals(id, orderItem.getId());
        assertEquals(offer, orderItem.getOffer());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(priceAtTimeOfOrder, orderItem.getPriceAtTimeOfOrder());
        assertEquals(createdAt, orderItem.getCreatedAt());
        assertEquals(updatedAt, orderItem.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        id = null;
        quantity = 3;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("ID cannot be null for existing OrderItem when calling withId"));
    }

    @Test
    void withId_WithNullOffer_ShouldThrowException() {
        id = 1L;
        quantity = 3;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.withId(id, null, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("offer cannot be null"));
    }

    @Test
    void withId_WithZeroQuantity_ShouldThrowException() {
        id = 1L;
        quantity = 0;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

    @Test
    void withId_WithNegativeQuantity_ShouldThrowException() {
        id = 1L;
        quantity = -1;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

    @Test
    void withId_WithZeroPrice_ShouldThrowException() {
        id = 1L;
        quantity = 3;
        priceAtTimeOfOrder = 0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("priceAtTimeOfOrder cannot be negative or zero"));
    }

    @Test
    void withId_WithNegativePrice_ShouldThrowException() {
        id = 1L;
        quantity = 3;
        priceAtTimeOfOrder = -10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("priceAtTimeOfOrder cannot be negative or zero"));
    }

//  createForPersistenceHydration()

    @Test
    void createForPersistenceHydration_WithValidData_ShouldReturnOrderItem() {
        id = 1L;
        quantity = 2;
        priceAtTimeOfOrder = 25.50;
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItem orderItem = OrderItem.createForPersistenceHydration(id, quantity, priceAtTimeOfOrder, createdAt, updatedAt);

        assertNotNull(orderItem);
        assertEquals(id, orderItem.getId());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(priceAtTimeOfOrder, orderItem.getPriceAtTimeOfOrder());
        assertEquals(createdAt, orderItem.getCreatedAt());
        assertEquals(updatedAt, orderItem.getUpdatedAt());
        assertNull(orderItem.getOffer());
    }

    @Test
    void createForPersistenceHydration_WithZeroQuantity_ShouldThrowException() {
        id = 1L;
        quantity = 0;
        priceAtTimeOfOrder = 25.50;
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        assertDoesNotThrow(() -> {
            OrderItem.createForPersistenceHydration(id, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });
    }

    @Test
    void createForPersistenceHydration_WithNegativeQuantity_ShouldThrowException() {
        id = 1L;
        quantity = -5;
        priceAtTimeOfOrder = 25.50;
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        assertDoesNotThrow(() -> {
            OrderItem.createForPersistenceHydration(id, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });
    }

    @Test
    void createForPersistenceHydration_WithZeroPrice_ShouldThrowException() {
        id = 1L;
        quantity = 2;
        priceAtTimeOfOrder = 0;
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        assertDoesNotThrow(() -> {
            OrderItem.createForPersistenceHydration(id, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });
    }

    @Test
    void createForPersistenceHydration_WithNegativePrice_ShouldThrowException() {
        id = 1L;
        quantity = 2;
        priceAtTimeOfOrder = -10.0;
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        assertDoesNotThrow(() -> {
            OrderItem.createForPersistenceHydration(id, quantity, priceAtTimeOfOrder, createdAt, updatedAt);
        });
    }

//  updateQuantity()

    @Test
    void updateQuantity_WithValidQuantity_ShouldUpdateQuantity() {
        id = 1L;
        quantity = 5;
        int newQuantity = 10;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItem orderItem = OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);

        orderItem.updateQuantity(newQuantity);

        assertEquals(newQuantity, orderItem.getQuantity());
        assertNotEquals(updatedAt, orderItem.getUpdatedAt());
    }

    @Test
    void updateQuantity_WithZeroQuantity_ShouldThrowException() {
        id = 1L;
        quantity = 5;
        int newQuantity = 0;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItem orderItem = OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            orderItem.updateQuantity(newQuantity);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

    @Test
    void updateQuantity_WithNegativeQuantity_ShouldThrowException() {
        id = 1L;
        quantity = 5;
        int newQuantity = -10;
        priceAtTimeOfOrder = 10.0;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderItem orderItem = OrderItem.withId(id, offer, quantity, priceAtTimeOfOrder, createdAt, updatedAt);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            orderItem.updateQuantity(newQuantity);
        });

        assertTrue(ex.getMessage().startsWith("OrderItem validation failed:"));
        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }
}
