package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateWebRequest {
    private Long offerId;
    private int quantity;
}
