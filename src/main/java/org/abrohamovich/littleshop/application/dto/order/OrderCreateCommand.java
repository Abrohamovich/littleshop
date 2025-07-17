package org.abrohamovich.littleshop.application.dto.order;

import lombok.*;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemCreateCommand;

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
