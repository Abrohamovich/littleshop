package org.abrohamovich.littleshop.application.usecase.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
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
public class UpdateOrderServiceTest {
    private final Long orderId = 1L;
    private final Long customerId = 10L;
    private final Long newCustomerId = 11L;
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @InjectMocks
    private UpdateOrderService updateOrderService;
    private Order testOrder;
    private Customer oldCustomer;
    private Customer newCustomer;
    private OrderUpdateCommand command;
    private User testUser;
    private OrderItem testOrderItem;
    private Offer testOffer;

    @BeforeEach
    void setUp() {
        oldCustomer = Customer.withId(customerId, "Old Customer", "old@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        newCustomer = Customer.withId(newCustomerId, "New Customer", "new@mail.com", "222", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testUser = User.withId(1L, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "333", LocalDateTime.now(), LocalDateTime.now());
        Category testCategory = Category.withId(1L, "Test Category", "Desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier testSupplier = Supplier.withId(1L, "Test Supplier", "test@mail.com", "444", "Address", "Desc", LocalDateTime.now(), LocalDateTime.now());
        testOffer = Offer.withId(10L, "Test Offer", 10.0, OfferType.PRODUCT, "Description", testCategory, testSupplier, LocalDateTime.now(), LocalDateTime.now());
        testOrderItem = OrderItem.createNew(testOffer, 2);

        testOrder = Order.withId(orderId, oldCustomer, testUser, OrderStatus.IN_PROGRESS, List.of(testOrderItem), LocalDateTime.now(), LocalDateTime.now());
        command = new OrderUpdateCommand(newCustomerId);
    }

    @Test
    void update_shouldUpdateCustomerAndReturnOrderResponse_whenOrderAndCustomerExist() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(customerRepositoryPort.findById(newCustomerId)).thenReturn(Optional.of(newCustomer));

        Order updatedOrder = Order.withId(orderId, newCustomer, testUser, OrderStatus.IN_PROGRESS, List.of(testOrderItem), testOrder.getCreatedAt(), LocalDateTime.now());
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(updatedOrder);

        OrderResponse response = updateOrderService.update(orderId, command);

        assertNotNull(response);
        assertEquals(newCustomerId, response.getCustomer().getId());
        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(customerRepositoryPort, times(1)).findById(newCustomerId);
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
    }

    @Test
    void update_shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
        Long nonExistentId = 99L;
        when(orderRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                updateOrderService.update(nonExistentId, command)
        );

        verify(orderRepositoryPort, times(1)).findById(nonExistentId);
        verify(customerRepositoryPort, never()).findById(anyLong());
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }

    @Test
    void update_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        Long nonExistentCustomerId = 99L;
        OrderUpdateCommand commandWithNonExistentCustomer = new OrderUpdateCommand(nonExistentCustomerId);
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(customerRepositoryPort.findById(nonExistentCustomerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                updateOrderService.update(orderId, commandWithNonExistentCustomer)
        );

        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(customerRepositoryPort, times(1)).findById(nonExistentCustomerId);
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }
}