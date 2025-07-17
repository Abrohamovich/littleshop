package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.port.in.order.GetOrderUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GetOrderService implements GetOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public OrderResponse findById(Long id) {
        return orderRepositoryPort.findById(id)
                .map(OrderResponse::toResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + id + "' not found."));
    }

    @Override
    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderRepositoryPort.findAll(pageable)
                .map(OrderResponse::toResponse);
    }

    @Override
    public Page<OrderResponse> findByCustomerId(Long customerId, Pageable pageable) {
        return orderRepositoryPort.findByCustomerId(customerId, pageable)
                .map(OrderResponse::toResponse);
    }

    @Override
    public Page<OrderResponse> findByUserId(Long userId, Pageable pageable) {
        return orderRepositoryPort.findByUserId(userId, pageable)
                .map(OrderResponse::toResponse);
    }
}
