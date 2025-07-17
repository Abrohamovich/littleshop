package org.abrohamovich.littleshop.application.port.in.order;

import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateCommand;

public interface ChangeOrderStatusUseCase {
    OrderResponse changeStatus(OrderUpdateCommand command);
}
