package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateStatusCommand;

public interface ChangeOrderStatusUseCase {
    OrderResponse changeStatus(Long orderId, OrderUpdateStatusCommand command);
}
