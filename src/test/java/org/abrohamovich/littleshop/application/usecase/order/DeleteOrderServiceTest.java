package org.abrohamovich.littleshop.application.usecase.order;

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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteOrderServiceTest {
    private final Long orderId = 1L;
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @InjectMocks
    private DeleteOrderService deleteOrderService;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        Customer testCustomer = Customer.withId(1L, "CustName", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        User testUser = User.withId(1L, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        Category testCategory = Category.withId(1L, "Test Category", "Desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier testSupplier = Supplier.withId(1L, "Test Supplier", "test@mail.com", "1234567890", "Address", "Desc", LocalDateTime.now(), LocalDateTime.now());
        Offer testOffer = Offer.withId(10L, "Test Offer", 10.0, OfferType.PRODUCT, "Description", testCategory, testSupplier, LocalDateTime.now(), LocalDateTime.now());
        OrderItem testOrderItem = OrderItem.createNew(testOffer, 2);

        testOrder = Order.withId(orderId, testCustomer, testUser, OrderStatus.IN_PROGRESS, List.of(testOrderItem), LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void deleteById_shouldDeleteOrder_whenOrderExists() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));

        deleteOrderService.deleteById(orderId);

        verify(orderRepositoryPort, times(1)).findById(orderId);
        verify(orderRepositoryPort, times(1)).deleteById(orderId);
    }

    @Test
    void deleteById_shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
        Long nonExistentId = 99L;
        when(orderRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                deleteOrderService.deleteById(nonExistentId)
        );

        verify(orderRepositoryPort, times(1)).findById(nonExistentId);
        verify(orderRepositoryPort, never()).deleteById(anyLong());
    }
}