package org.abrohamovich.littleshop.application.dto.order;

import lombok.*;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemCreateCommand;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.abrohamovich.littleshop.domain.model.OrderItem;
import org.abrohamovich.littleshop.domain.model.OrderStatus;
import org.abrohamovich.littleshop.domain.model.User;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateCommand {
    private Long customerId;
    private Long userId;
    private List<OrderItemCreateCommand> items;
}
