package org.abrohamovich.littleshop.application.usecase.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemAddToOrderCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
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
public class AddOrderItemToOrderServiceTest {
    private final Long orderId = 1L;
    private final Long initialOfferId = 10L;
    private final Long newOfferId = 20L;
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @Mock
    private OfferRepositoryPort offerRepositoryPort;
    @InjectMocks
    private AddOrderItemToOrderService addOrderItemToOrderService;
    private Order testOrder;
    private Offer initialOffer;
    private Offer newOffer;
    private OrderItemAddToOrderCommand command;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.withId(1L, "CustName", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        User user = User.withId(1L, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        Category category = Category.withId(1L, "Category", "desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier supplier = Supplier.withId(1L, "Supplier", "mail", "phone", "address", "desc", LocalDateTime.now(), LocalDateTime.now());

        initialOffer = Offer.withId(initialOfferId, "Initial Offer", 100.0, OfferType.PRODUCT, "desc", category, supplier, LocalDateTime.now(), LocalDateTime.now());
        OrderItem initialItem = OrderItem.withId(1L, initialOffer, 1, initialOffer.getPrice(), LocalDateTime.now(), LocalDateTime.now());

        testOrder = Order.withId(orderId, customer, user, OrderStatus.IN_PROGRESS, List.of(initialItem), LocalDateTime.now(), LocalDateTime.now());

        newOffer = Offer.withId(newOfferId, "New Offer", 50.0, OfferType.PRODUCT, "desc", category, supplier, LocalDateTime.now(), LocalDateTime.now());
        command = new OrderItemAddToOrderCommand(newOfferId, 2);
    }

    @Test
    void add_shouldAddOrderItemAndReturnOrderResponse_whenOrderAndOfferExist() {
        // Arrange
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(offerRepositoryPort.findById(newOfferId)).thenReturn(Optional.of(newOffer));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(testOrder);

        // The total price before adding the new item
        double initialTotalPrice = testOrder.totalPrice();

        // Act
        OrderResponse response = addOrderItemToOrderService.add(orderId, command);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getItems().size());
        assertEquals(initialTotalPrice + (newOffer.getPrice() * command.getQuantity()), response.getTotalPrice());
        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(offerRepositoryPort, times(1)).findById(newOfferId);
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
    }

    @Test
    void add_shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
        Long nonExistentOrderId = 99L;
        when(orderRepositoryPort.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                addOrderItemToOrderService.add(nonExistentOrderId, command)
        );

        verify(orderRepositoryPort, times(1)).findById(nonExistentOrderId);
        verify(offerRepositoryPort, never()).findById(anyLong());
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }

    @Test
    void add_shouldThrowOfferNotFoundException_whenOfferDoesNotExist() {
        Long nonExistentOfferId = 99L;
        OrderItemAddToOrderCommand commandWithNonExistentOffer = new OrderItemAddToOrderCommand(nonExistentOfferId, 2);

        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(offerRepositoryPort.findById(nonExistentOfferId)).thenReturn(Optional.empty());

        assertThrows(OfferNotFoundException.class, () ->
                addOrderItemToOrderService.add(orderId, commandWithNonExistentOffer)
        );

        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(offerRepositoryPort, times(1)).findById(nonExistentOfferId);
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }
}
