package org.abrohamovich.littleshop.application.usecase.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemRemoveFromOrderCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveOrderItemFromOrderServiceTest {
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @InjectMocks
    private  RemoveOrderItemFromOrderService removeOrderItemFromOrderService;

    private Customer testCustomer;
    private User testUser;
    private Offer testOffer;
    private OrderItemRemoveFromOrderCommand removeCommand;
    private final Long orderId = 1L;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.withId(1L, "CustName", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testUser = User.withId(1L, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        Category testCategory = Category.withId(1L, "Test Category", "Desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier testSupplier = Supplier.withId(1L, "Test Supplier", "test@mail.com", "1234567890", "Address", "Desc", LocalDateTime.now(), LocalDateTime.now());
        testOffer = Offer.withId(10L, "Test Offer", 10.0, OfferType.PRODUCT, "Description", testCategory, testSupplier, LocalDateTime.now(), LocalDateTime.now());
        OrderItem testOrderItem = OrderItem.withId(1L, testOffer, 2, 10, LocalDateTime.now(), LocalDateTime.now());
        OrderItem testOrderItem2 = OrderItem.withId(2L, testOffer, 3, 10, LocalDateTime.now(), LocalDateTime.now());
        testOrder = Order.withId(orderId, testCustomer, testUser, OrderStatus.IN_PROGRESS, List.of(testOrderItem, testOrderItem2), LocalDateTime.now(), LocalDateTime.now());
        removeCommand = new OrderItemRemoveFromOrderCommand(testOrderItem.getId());
    }

    @Test
    void remove_shouldReturnOrderResponse_whenOrderExists() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));

        Order savedOrder = Order.createNew(testCustomer, testUser, List.of(OrderItem.createNew(testOffer, 3)));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponse response = removeOrderItemFromOrderService.remove(orderId, removeCommand);

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
        assertEquals(30.0, response.getTotalPrice());
        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
    }

    @Test
    void remove_shouldThrowException_whenOrderDoesNotExist() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.empty());

        Exception ex = assertThrows(OrderNotFoundException.class,
                () -> {removeOrderItemFromOrderService.remove(orderId, removeCommand);});

        assertTrue(ex.getMessage().contains("Order with ID '" + orderId + "' not found."));
        verify(orderRepositoryPort, times(1)).findById(orderId);
    }

}