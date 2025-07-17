package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetOrderUseCase {
    OrderResponse getOrder(Long id);
    Page<OrderResponse> getAllOrders(Pageable pageable);
    Page<OrderResponse> getOrdersByCustomerId(Long customerId, Pageable pageable);
    Page<OrderResponse> getOrdersByUserId(Long userId, Pageable pageable);
}
