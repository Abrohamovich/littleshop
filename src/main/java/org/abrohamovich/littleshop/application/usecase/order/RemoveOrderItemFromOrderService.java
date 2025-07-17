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
    public OrderResponse remove(OrderItemRemoveFromOrderCommand command) {
        Order order = orderRepositoryPort.findById(command.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + command.getOrderId() + "' not found."));

        order.removeOrderItem(command.getOrderItemId());

        return OrderResponse.toResponse(order);
    }
}
