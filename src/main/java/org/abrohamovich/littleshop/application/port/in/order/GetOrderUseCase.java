package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetOrderUseCase {
    OrderResponse findById(Long id);
    Page<OrderResponse> findAll(Pageable pageable);
    Page<OrderResponse> findByCustomerId(Long customerId, Pageable pageable);
    Page<OrderResponse> findByUserId(Long userId, Pageable pageable);
}
