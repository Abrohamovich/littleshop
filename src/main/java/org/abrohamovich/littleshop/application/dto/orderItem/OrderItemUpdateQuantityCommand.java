package org.abrohamovich.littleshop.application.dto.orderItem;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemUpdateQuantityCommand {
    private Long orderId;
    private Long orderItemId;
    private int newQuantity;
}
