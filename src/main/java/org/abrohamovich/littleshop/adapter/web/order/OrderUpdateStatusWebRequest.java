package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.OrderStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateStatusWebRequest {
    private OrderStatus status;
}
