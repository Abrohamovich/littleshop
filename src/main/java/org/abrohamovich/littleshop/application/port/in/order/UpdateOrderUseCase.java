package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateCommand;

public interface UpdateOrderUseCase {
    OrderResponse updateOrder(OrderUpdateCommand command);
}
