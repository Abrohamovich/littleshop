package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemUpdateQuantityWebRequest {
    private Long orderId;
    private Long orderItemId;
    private int newQuantity;
}
