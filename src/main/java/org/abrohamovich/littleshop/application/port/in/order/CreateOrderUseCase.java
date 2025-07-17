package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderCreateCommand;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;

public interface CreateOrderUseCase {
    OrderResponse save(OrderCreateCommand command);
}
