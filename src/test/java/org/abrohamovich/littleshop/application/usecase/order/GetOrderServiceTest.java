package org.abrohamovich.littleshop.application.usecase.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOrderServiceTest {
    private final Long orderId = 1L;
    private final Long customerId = 10L;
    private final Long userId = 20L;
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @InjectMocks
    private GetOrderService getOrderService;
    private Order testOrder;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        Customer testCustomer = Customer.withId(customerId, "CustName", "cust@mail.com", "111", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        User testUser = User.withId(userId, "UserName", "UserSurname", "user@mail.com", "hashed", UserRole.WORKER, "222", LocalDateTime.now(), LocalDateTime.now());
        Category testCategory = Category.withId(1L, "Test Category", "Desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier testSupplier = Supplier.withId(1L, "Test Supplier", "test@mail.com", "1234567890", "Address", "Desc", LocalDateTime.now(), LocalDateTime.now());
        Offer testOffer = Offer.withId(10L, "Test Offer", 10.0, OfferType.PRODUCT, "Description", testCategory, testSupplier, LocalDateTime.now(), LocalDateTime.now());
        OrderItem testOrderItem = OrderItem.createNew(testOffer, 2);

        testOrder = Order.withId(orderId, testCustomer, testUser, OrderStatus.IN_PROGRESS, List.of(testOrderItem), LocalDateTime.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_shouldReturnOrderResponse_whenOrderExists() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(testOrder));

        OrderResponse response = getOrderService.findById(orderId);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
        assertEquals(OrderStatus.IN_PROGRESS, response.getStatus());
        assertEquals(2, response.getItems().get(0).getQuantity());
        verify(orderRepositoryPort).findById(orderId);
    }

    @Test
    void findById_shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> getOrderService.findById(orderId));
        verify(orderRepositoryPort).findById(orderId);
    }

    @Test
    void findAll_shouldReturnPageOfOrderResponses_whenOrdersExist() {
        Page<Order> orderPage = new PageImpl<>(List.of(testOrder), pageable, 1);
        when(orderRepositoryPort.findAll(pageable)).thenReturn(orderPage);

        Page<OrderResponse> responsePage = getOrderService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(orderId, responsePage.getContent().get(0).getId());
        verify(orderRepositoryPort).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoOrdersExist() {
        Page<Order> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(orderRepositoryPort.findAll(pageable)).thenReturn(emptyPage);

        Page<OrderResponse> responsePage = getOrderService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(orderRepositoryPort).findAll(pageable);
    }

    @Test
    void findByCustomerId_shouldReturnPageOfOrderResponses_whenMatchesExist() {
        Page<Order> orderPage = new PageImpl<>(List.of(testOrder), pageable, 1);
        when(orderRepositoryPort.findByCustomerId(customerId, pageable)).thenReturn(orderPage);

        Page<OrderResponse> responsePage = getOrderService.findByCustomerId(customerId, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(orderId, responsePage.getContent().get(0).getId());
        verify(orderRepositoryPort).findByCustomerId(customerId, pageable);
    }

    @Test
    void findByCustomerId_shouldReturnEmptyPage_whenNoMatchesFound() {
        Long nonExistentId = 99L;
        Page<Order> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(orderRepositoryPort.findByCustomerId(nonExistentId, pageable)).thenReturn(emptyPage);

        Page<OrderResponse> responsePage = getOrderService.findByCustomerId(nonExistentId, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(orderRepositoryPort).findByCustomerId(nonExistentId, pageable);
    }

    @Test
    void findByUserId_shouldReturnPageOfOrderResponses_whenMatchesExist() {
        Page<Order> orderPage = new PageImpl<>(List.of(testOrder), pageable, 1);
        when(orderRepositoryPort.findByUserId(userId, pageable)).thenReturn(orderPage);

        Page<OrderResponse> responsePage = getOrderService.findByUserId(userId, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(orderId, responsePage.getContent().get(0).getId());
        verify(orderRepositoryPort).findByUserId(userId, pageable);
    }

    @Test
    void findByUserId_shouldReturnEmptyPage_whenNoMatchesFound() {
        Long nonExistentId = 99L;
        Page<Order> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(orderRepositoryPort.findByUserId(nonExistentId, pageable)).thenReturn(emptyPage);

        Page<OrderResponse> responsePage = getOrderService.findByUserId(nonExistentId, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(orderRepositoryPort).findByUserId(nonExistentId, pageable);
    }
}
