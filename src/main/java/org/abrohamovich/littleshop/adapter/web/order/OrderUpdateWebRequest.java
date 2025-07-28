package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateWebRequest {
    private Long customerId;
    private Long userId;
}
