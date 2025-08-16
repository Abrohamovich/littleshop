package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateStatusCommand;
import org.abrohamovich.littleshop.application.port.in.order.ChangeOrderStatusUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.Order;

@RequiredArgsConstructor
public class ChangeOrderStatusService implements ChangeOrderStatusUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public OrderResponse changeStatus(Long orderId, OrderUpdateStatusCommand command) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + orderId + "' not found."));

        order.changeStatus(command.getStatus());
        Order savedOrder = orderRepositoryPort.save(order);

        return OrderResponse.toResponse(savedOrder);
    }
}
