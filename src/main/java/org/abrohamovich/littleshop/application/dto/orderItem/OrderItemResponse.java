package org.abrohamovich.littleshop.application.dto.orderItem;

import lombok.*;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.domain.model.OrderItem;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private OfferResponse offer;
    private int quantity;
    private double priceAtTimeOfOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderItemResponse toResponse(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("OrderItem cannot be null to continue the conversion");
        }
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .offer(OfferResponse.toResponse(orderItem.getOffer()))
                .quantity(orderItem.getQuantity())
                .priceAtTimeOfOrder(orderItem.getPriceAtTimeOfOrder())
                .createdAt(orderItem.getCreatedAt())
                .updatedAt(orderItem.getUpdatedAt())
                .build();
    }
}
