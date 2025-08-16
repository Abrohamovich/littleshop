package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRemoveFromOrderWebRequest {
    private Long orderItemId;
}
