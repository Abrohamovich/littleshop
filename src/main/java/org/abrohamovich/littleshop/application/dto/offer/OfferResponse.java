package org.abrohamovich.littleshop.application.dto.offer;

import lombok.*;
import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.OfferType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse {
    private Long id;
    private String name;
    private double price;
    private OfferType type;
    private String description;
    private CategoryResponse category;
    private SupplierResponse supplier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OfferResponse toResponse(Offer offer) {
        if (offer == null) {
            throw new IllegalArgumentException("Offer cannot be null to continue the conversion.");
        }
        return OfferResponse.builder()
                .id(offer.getId())
                .name(offer.getName())
                .price(offer.getPrice())
                .type(offer.getType())
                .category(CategoryResponse.toResponse(offer.getCategory()))
                .supplier(SupplierResponse.toResponse(offer.getSupplier()))
                .description(offer.getDescription())
                .createdAt(offer.getCreatedAt())
                .updatedAt(offer.getUpdatedAt())
                .build();
    }
}
