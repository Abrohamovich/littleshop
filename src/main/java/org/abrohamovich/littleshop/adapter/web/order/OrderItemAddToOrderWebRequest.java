package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAddToOrderWebRequest {
    private Long offerId;
    private int quantity;
}
