package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemRemoveFromOrderCommand;
import org.abrohamovich.littleshop.application.port.in.order.RemoveOrderItemFromOrderUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.Order;

@RequiredArgsConstructor
public class RemoveOrderItemFromOrderService implements RemoveOrderItemFromOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public OrderResponse remove(Long id, OrderItemRemoveFromOrderCommand command) {
        Order order = orderRepositoryPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + id + "' not found."));

        order.removeOrderItem(command.getOrderItemId());
        Order savedOrder = orderRepositoryPort.save(order);

        return OrderResponse.toResponse(savedOrder);
    }
}
