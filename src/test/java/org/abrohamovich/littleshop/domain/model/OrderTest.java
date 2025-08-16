package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.order.OrderValidationException;
import org.abrohamovich.littleshop.domain.exception.orderItem.OrderItemNotFoundException;
import org.abrohamovich.littleshop.domain.exception.orderItem.OrderItemValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Long id;
    private List<OrderItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Customer customer;
    private User user;
    private Offer offer;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        customer = Customer.withId(1L, "John", "Doe", "john.doe@example.com", "123-456-7890", "123 Main St", LocalDateTime.now(), LocalDateTime.now());
        user = User.withId(1L, "Jane", "Smith", "jane.smith@example.com", "hashedpassword", UserRole.WORKER, "098-765-4321", LocalDateTime.now(), LocalDateTime.now());
        offer = Offer.withId(1L, "Test Offer", 10.0, OfferType.PRODUCT, "A test product",
                Category.withId(1L, "Category", "Description", LocalDateTime.now(), LocalDateTime.now()),
                Supplier.withId(1L, "Supplier", "test@test.com", "123", "address", "desc", LocalDateTime.now(), LocalDateTime.now()),
                LocalDateTime.now(), LocalDateTime.now());
    }

    @AfterEach
    void tearDown() {
        id = null;
        items = null;
        createdAt = null;
        updatedAt = null;
        customer = null;
        user = null;
        offer = null;
    }

// createNew()

    @Test
    void createNew_WithValidData_ShouldReturnOrder() {
        items.add(OrderItem.createNew(offer, 2));

        Order order = Order.createNew(customer, user, items);

        assertNotNull(order);
        assertNull(order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(user, order.getUser());
        assertEquals(OrderStatus.IN_PROGRESS, order.getStatus());
        assertEquals(items, order.getItems());
        assertNotNull(order.getCreatedAt());
        assertNotNull(order.getUpdatedAt());
    }

    @Test
    void createNew_WithNullCustomer_ShouldThrowException() {
        items.add(OrderItem.createNew(offer, 2));

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.createNew(null, user, items);
        });

        assertTrue(ex.getMessage().contains("customer cannot be null"));
    }

    @Test
    void createNew_WithNullUser_ShouldThrowException() {
        items.add(OrderItem.createNew(offer, 2));

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.createNew(customer, null, items);
        });

        assertTrue(ex.getMessage().contains("user cannot be null"));
    }

    @Test
    void createNew_WithEmptyItemsList_ShouldThrowException() {
        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.createNew(customer, user, items);
        });

        assertTrue(ex.getMessage().contains("order must contain at least one item"));
    }

    @Test
    void createNew_WithNullItemsList_ShouldThrowException() {
        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.createNew(customer, user, null);
        });

        assertTrue(ex.getMessage().contains("order must contain at least one item"));
    }

// withId()

    @Test
    void withId_WithValidData_ShouldReturnOrder() {
        id = 1L;
        items.add(OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now()));
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        Order order = Order.withId(id, customer, user, OrderStatus.COMPLETED, items, createdAt, updatedAt);

        assertNotNull(order);
        assertEquals(id, order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(user, order.getUser());
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        assertEquals(items, order.getItems());
        assertEquals(createdAt, order.getCreatedAt());
        assertEquals(updatedAt, order.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        id = null;
        items.add(OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now()));
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Order.withId(id, customer, user, OrderStatus.COMPLETED, items, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().contains("ID cannot be null for existing Order"));
    }

    @Test
    void withId_WithNullCustomer_ShouldThrowException() {
        id = 1L;
        items.add(OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now()));
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.withId(id, null, user, OrderStatus.COMPLETED, items, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().contains("customer cannot be null"));
    }

    @Test
    void withId_WithNullUser_ShouldThrowException() {
        id = 1L;
        items.add(OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now()));
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.withId(id, customer, null, OrderStatus.COMPLETED, items, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().contains("user cannot be null"));
    }

    @Test
    void withId_WithNullStatus_ShouldThrowException() {
        id = 1L;
        items.add(OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now()));
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.withId(id, customer, user, null, items, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().contains("status cannot be null"));
    }

    @Test
    void withId_WithEmptyItemsList_ShouldThrowException() {
        id = 1L;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.withId(id, customer, user, OrderStatus.COMPLETED, items, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().contains("order must contain at least one item"));
    }

    @Test
    void withId_WithNullItemsList_ShouldThrowException() {
        id = 1L;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            Order.withId(id, customer, user, OrderStatus.COMPLETED, null, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().contains("order must contain at least one item"));
    }

// createForPersistenceHydration()

    @Test
    void createForPersistenceHydration_WithValidData_ShouldReturnOrder() {
        id = 1L;
        createdAt = LocalDateTime.now().minusDays(2);
        updatedAt = LocalDateTime.now().minusDays(1);

        Order order = Order.createForPersistenceHydration(id, createdAt, OrderStatus.IN_PROGRESS, updatedAt);

        assertNotNull(order);
        assertEquals(id, order.getId());
        assertEquals(createdAt, order.getCreatedAt());
        assertEquals(OrderStatus.IN_PROGRESS, order.getStatus());
        assertEquals(updatedAt, order.getUpdatedAt());
        assertNull(order.getCustomer());
        assertNull(order.getUser());
        assertTrue(order.getItems().isEmpty());
    }

// updateDetails()

    @Test
    void updateDetails_WithValidData_ShouldUpdateCustomer() {
        items.add(OrderItem.createNew(offer, 2));
        Order order = Order.createNew(customer, user, items);
        Customer newCustomer = Customer.withId(2L, "Jane", "Doe", "jane.doe@example.com", "123-456-7890", "456 Side St", LocalDateTime.now(), LocalDateTime.now());

        order.updateDetails(newCustomer);

        assertEquals(newCustomer, order.getCustomer());
    }

    @Test
    void updateDetails_WithNullCustomer_ShouldThrowException() {
        items.add(OrderItem.createNew(offer, 2));
        Order order = Order.createNew(customer, user, items);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            order.updateDetails(null);
        });

        assertTrue(ex.getMessage().contains("customer cannot be null"));
    }

// addOrderItem()

    @Test
    void addOrderItem_WithValidData_ShouldAddOrderItem() {
        Order order = Order.createNew(customer, user, new ArrayList<>(Collections.singletonList(OrderItem.createNew(offer, 1))));
        int initialItemCount = order.getItems().size();
        Offer newOffer = Offer.createNew("New Offer", 50.0, OfferType.SERVICE, "A new service",
                Category.withId(2L, "Services", "Digital", LocalDateTime.now(), LocalDateTime.now()),
                Supplier.withId(2L, "ServiceCorp", "service@corp.com", "321", "addr C", "desc C", LocalDateTime.now(), LocalDateTime.now()));

        order.addOrderItem(newOffer, 3);

        assertEquals(initialItemCount + 1, order.getItems().size());
        assertTrue(order.getItems().stream().anyMatch(item -> item.getOffer().getName().equals("New Offer") && item.getQuantity() == 3));
    }

    @Test
    void addOrderItem_WithNullOffer_ShouldThrowException() {
        Order order = Order.createNew(customer, user, new ArrayList<>(Collections.singletonList(OrderItem.createNew(offer, 1))));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(null, 5);
        });

        assertTrue(ex.getMessage().contains("Offer cannot be null when creating new OrderItem."));
    }

    @Test
    void addOrderItem_WithZeroQuantity_ShouldThrowException() {
        Order order = Order.createNew(customer, user, new ArrayList<>(Collections.singletonList(OrderItem.createNew(offer, 1))));

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            order.addOrderItem(offer, 0);
        });

        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

// updateOrderItemQuantity()

    @Test
    void updateOrderItemQuantity_WithValidIdAndQuantity_ShouldUpdateQuantity() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        Order order = Order.createNew(customer, user, items);
        int newQuantity = 5;

        order.updateOrderItemQuantity(item1.getId(), newQuantity);

        assertEquals(newQuantity, order.getItems().get(0).getQuantity());
    }

    @Test
    void updateOrderItemQuantity_WithNonExistentId_ShouldThrowException() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        Order order = Order.createNew(customer, user, items);

        OrderItemNotFoundException ex = assertThrows(OrderItemNotFoundException.class, () -> {
            order.updateOrderItemQuantity(99L, 5);
        });

        assertTrue(ex.getMessage().contains("Order item with ID 99 not found in this order."));
    }

    @Test
    void updateOrderItemQuantity_WithZeroQuantity_ShouldThrowException() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        Order order = Order.createNew(customer, user, items);

        OrderItemValidationException ex = assertThrows(OrderItemValidationException.class, () -> {
            order.updateOrderItemQuantity(item1.getId(), 0);
        });

        assertTrue(ex.getMessage().contains("quantity cannot be negative or zero"));
    }

// removeOrderItem()

    @Test
    void removeOrderItem_WithValidId_ShouldRemoveOrderItem() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        OrderItem item2 = OrderItem.withId(2L, offer, 5, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        items.add(item2);
        Order order = Order.createNew(customer, user, items);
        int initialItemCount = order.getItems().size();

        order.removeOrderItem(item1.getId());

        assertEquals(initialItemCount - 1, order.getItems().size());
        assertFalse(order.getItems().contains(item1));
    }

    @Test
    void removeOrderItem_WithNonExistentId_ShouldThrowException() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        Order order = Order.createNew(customer, user, items);

        OrderItemNotFoundException ex = assertThrows(OrderItemNotFoundException.class, () -> {
            order.removeOrderItem(99L);
        });

        assertTrue(ex.getMessage().contains("Order item with ID 99 not found in this order to remove."));
    }

    @Test
    void removeOrderItem_WithLastItem_ShouldThrowException() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        Order order = Order.createNew(customer, user, items);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            order.removeOrderItem(item1.getId());
        });

        assertTrue(ex.getMessage().contains("order must contain at least one item"));
    }

// changeStatus()

    @Test
    void changeStatus_WithValidStatus_ShouldChangeStatus() {
        items.add(OrderItem.createNew(offer, 2));
        Order order = Order.createNew(customer, user, items);

        order.changeStatus(OrderStatus.IN_PROGRESS);

        assertEquals(OrderStatus.IN_PROGRESS, order.getStatus());
    }

    @Test
    void changeStatus_WithNullStatus_ShouldThrowException() {
        items.add(OrderItem.createNew(offer, 2));
        Order order = Order.createNew(customer, user, items);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            order.changeStatus(null);
        });

        assertTrue(ex.getMessage().contains("New order status cannot be null."));
    }

    @Test
    void changeStatus_FromCancelled_ShouldThrowException() {
        id = 1L;
        items.add(OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now()));
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        Order order = Order.withId(id, customer, user, OrderStatus.CANCELLED, items, createdAt, updatedAt);

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> {
            order.changeStatus(OrderStatus.IN_PROGRESS);
        });

        assertTrue(ex.getMessage().contains("Cannot change status from CANCELLED."));
    }

// totalPrice()

    @Test
    void totalPrice_WithMultipleItems_ShouldCalculateCorrectTotal() {
        items.add(OrderItem.createNew(offer, 2));
        items.add(OrderItem.createNew(offer, 3));
        Order order = Order.createNew(customer, user, items);

        double expectedTotal = (2 * offer.getPrice()) + (3 * offer.getPrice());

        assertEquals(expectedTotal, order.totalPrice());
    }

    @Test
    void totalPrice_WithSingleItem_ShouldCalculateCorrectTotal() {
        items.add(OrderItem.createNew(offer, 5));
        Order order = Order.createNew(customer, user, items);

        double expectedTotal = 5 * offer.getPrice();

        assertEquals(expectedTotal, order.totalPrice());
    }

    @Test
    void totalPrice_WithUpdatedItemQuantity_ShouldCalculateCorrectTotal() {
        OrderItem item1 = OrderItem.withId(1L, offer, 2, offer.getPrice(), LocalDateTime.now(), LocalDateTime.now());
        items.add(item1);
        Order order = Order.createNew(customer, user, items);

        order.updateOrderItemQuantity(item1.getId(), 5);
        double expectedTotal = 5 * offer.getPrice();

        assertEquals(expectedTotal, order.totalPrice());
    }
}