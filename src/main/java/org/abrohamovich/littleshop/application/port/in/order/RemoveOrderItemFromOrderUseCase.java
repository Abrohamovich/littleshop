package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemRemoveFromOrderCommand;

public interface RemoveOrderItemFromOrderUseCase {
    OrderResponse removeOrderItemFromOrder(OrderItemRemoveFromOrderCommand command);
}
