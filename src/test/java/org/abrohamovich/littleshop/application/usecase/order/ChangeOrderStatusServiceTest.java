package org.abrohamovich.littleshop.application.usecase.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateStatusCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.exception.order.OrderValidationException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeOrderStatusServiceTest {
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @InjectMocks
    private ChangeOrderStatusService changeOrderStatusService;

    private final Long orderId = 1L;
    private Order testOrder;
    private OrderItem testOrderItem;
    private Category testCategory;
    private Supplier testSupplier;
    private Offer testOffer;
    private Customer testCustomer;
    private User testUser;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.withId(1L, "CustName", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testUser = User.withId(1L, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        testCategory = Category.withId(1L, "Test Category", "Desc", LocalDateTime.now(), LocalDateTime.now());
        testSupplier = Supplier.withId(1L, "Test Supplier", "test@mail.com", "1234567890", "Address", "Desc", LocalDateTime.now(), LocalDateTime.now());

        testOffer = Offer.withId(10L, "Test Offer", 10.0, OfferType.PRODUCT, "Description", testCategory, testSupplier, LocalDateTime.now(), LocalDateTime.now());

        testOrderItem = OrderItem.createNew(testOffer, 2);

        testOrder = Order.withId(orderId, testCustomer, testUser, OrderStatus.IN_PROGRESS, List.of(testOrderItem), LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void changeStatus_shouldUpdateStatusAndReturnOrderResponse_whenOrderExistsAndStatusChangeIsValid() {
        OrderUpdateStatusCommand command = new OrderUpdateStatusCommand(OrderStatus.COMPLETED);
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(testOrder);

        OrderResponse response = changeOrderStatusService.changeStatus(orderId, command);

        assertNotNull(response);
        assertEquals(OrderStatus.COMPLETED, response.getStatus());
        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
    }

    @Test
    void changeStatus_shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
        Long nonExistentId = 99L;
        OrderUpdateStatusCommand command = new OrderUpdateStatusCommand(OrderStatus.COMPLETED);
        when(orderRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                changeOrderStatusService.changeStatus(nonExistentId, command)
        );

        verify(orderRepositoryPort, times(1)).findById(nonExistentId);
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }

    @Test
    void changeStatus_shouldThrowOrderValidationException_whenChangingFromCancelled() {
        Customer customer = Customer.withId(1L, "CustName", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        User user = User.withId(1L, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        Order cancelledOrder = Order.withId(orderId, customer, user, OrderStatus.CANCELLED, List.of(testOrderItem), LocalDateTime.now(), LocalDateTime.now());
        OrderUpdateStatusCommand command = new OrderUpdateStatusCommand(OrderStatus.IN_PROGRESS);
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(cancelledOrder));

        assertThrows(OrderValidationException.class, () ->
                changeOrderStatusService.changeStatus(orderId, command)
        );

        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }
}
