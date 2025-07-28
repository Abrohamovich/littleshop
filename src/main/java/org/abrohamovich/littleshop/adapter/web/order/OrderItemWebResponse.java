package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;
import org.abrohamovich.littleshop.adapter.web.offer.OfferWebResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemWebResponse {
    private Long id;
    private OfferWebResponse offer;
    private int quantity;
    private double priceAtTimeOfOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
