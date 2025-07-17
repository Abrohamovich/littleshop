package org.abrohamovich.littleshop.application.dto.orderItem;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAddToOrderCommand {
    private Long offerId;
    private int quantity;
}
