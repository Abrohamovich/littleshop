package org.abrohamovich.littleshop.application.usecase.order;

import org.abrohamovich.littleshop.application.dto.order.OrderCreateCommand;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemCreateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
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
public class CreateOrderServiceTest {
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @Mock
    private OfferRepositoryPort offerRepositoryPort;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private CreateOrderService createOrderService;

    private OrderCreateCommand command;
    private Customer testCustomer;
    private User testUser;
    private Offer testOffer;
    private final Long customerId = 1L;
    private final Long userId = 2L;
    private final Long offerId = 10L;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.withId(customerId, "Customer", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testUser = User.withId(userId, "Worker", "Doe", "worker@mail.com", "hashed_pass", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        Category testCategory = Category.withId(1L, "Test Category", "desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier testSupplier = Supplier.withId(1L, "Test Supplier", "supp@mail.com", "333", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testOffer = Offer.withId(offerId, "Test Offer", 50.0, OfferType.PRODUCT, "desc", testCategory, testSupplier, LocalDateTime.now(), LocalDateTime.now());

        OrderItemCreateCommand itemCommand = new OrderItemCreateCommand(offerId, 3);
        command = new OrderCreateCommand(customerId, userId, List.of(itemCommand));
    }

    @Test
    void save_shouldCreateOrderAndReturnResponse_whenAllDependenciesExist() {
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(testOffer));

        Order savedOrder = Order.createNew(testCustomer, testUser, List.of(OrderItem.createNew(testOffer, 3)));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponse response = createOrderService.save(command);

        assertNotNull(response);
        assertEquals(OrderStatus.IN_PROGRESS, response.getStatus());
        assertEquals(1, response.getItems().size());
        assertEquals(150.0, response.getTotalPrice());
        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(userRepositoryPort, times(1)).findById(userId);
        verify(offerRepositoryPort, times(1)).findById(offerId);
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
    }

    @Test
    void save_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.empty());
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(testOffer));

        assertThrows(CustomerNotFoundException.class, () -> createOrderService.save(command));

        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(userRepositoryPort, never()).findById(anyLong());
        verify(offerRepositoryPort, times(1)).findById(anyLong());
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }

    @Test
    void save_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(testOffer));

        assertThrows(UserNotFoundException.class, () -> createOrderService.save(command));

        verify(customerRepositoryPort, times(1)).findById(customerId);
        verify(userRepositoryPort, times(1)).findById(userId);
        verify(offerRepositoryPort, times(1)).findById(anyLong());
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }

    @Test
    void save_shouldThrowOfferNotFoundException_whenOfferDoesNotExist() {
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(OfferNotFoundException.class, () -> createOrderService.save(command));

        verify(customerRepositoryPort, never()).findById(customerId);
        verify(userRepositoryPort, never()).findById(userId);
        verify(offerRepositoryPort, times(1)).findById(offerId);
        verify(orderRepositoryPort, never()).save(any(Order.class));
    }
}