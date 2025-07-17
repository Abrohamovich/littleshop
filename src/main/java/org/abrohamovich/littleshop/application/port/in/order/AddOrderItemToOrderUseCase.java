package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemAddToOrderCommand;

public interface AddOrderItemToOrderUseCase {
    OrderResponse add(Long orderId, OrderItemAddToOrderCommand command);
}
