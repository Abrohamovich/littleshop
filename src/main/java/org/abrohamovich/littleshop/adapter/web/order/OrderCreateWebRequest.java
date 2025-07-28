package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateWebRequest {
    private Long customerId;
    private Long userId;
    private List<OrderItemCreateWebRequest> items;
}
