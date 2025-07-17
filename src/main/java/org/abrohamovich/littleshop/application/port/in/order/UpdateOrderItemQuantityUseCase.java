package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemUpdateQuantityCommand;

public interface UpdateOrderItemQuantityUseCase {
    OrderResponse updateQuantity(OrderItemUpdateQuantityCommand command);
}
