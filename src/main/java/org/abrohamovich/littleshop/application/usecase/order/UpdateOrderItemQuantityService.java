package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemUpdateQuantityCommand;
import org.abrohamovich.littleshop.application.port.in.order.UpdateOrderItemQuantityUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.Order;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class UpdateOrderItemQuantityService implements UpdateOrderItemQuantityUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public OrderResponse updateQuantity(Long id, OrderItemUpdateQuantityCommand command) {
        Order order = orderRepositoryPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + id + "' not found."));

        order.updateOrderItemQuantity(command.getOrderItemId(), command.getNewQuantity());
        Order savedOrder = orderRepositoryPort.save(order);

        return OrderResponse.toResponse(savedOrder);
    }
}
