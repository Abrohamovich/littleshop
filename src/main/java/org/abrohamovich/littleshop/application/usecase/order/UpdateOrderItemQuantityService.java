package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemUpdateQuantityCommand;
import org.abrohamovich.littleshop.application.port.in.order.UpdateOrderItemQuantityUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.Order;

@RequiredArgsConstructor
public class UpdateOrderItemQuantityService implements UpdateOrderItemQuantityUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public OrderResponse updateQuantity(OrderItemUpdateQuantityCommand command) {
        Order order = orderRepositoryPort.findById(command.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + command.getOrderId() + "' not found."));

        order.updateOrderItemQuantity(command.getOrderItemId(), command.getNewQuantity());

        return OrderResponse.toResponse(order);
    }
}
