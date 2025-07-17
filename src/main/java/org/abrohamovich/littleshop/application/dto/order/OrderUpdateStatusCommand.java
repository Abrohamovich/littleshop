package org.abrohamovich.littleshop.application.dto.order;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.OrderStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateStatusCommand {
    private OrderStatus status;
}
