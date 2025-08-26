package org.abrohamovich.littleshop.adapter.web.offer;

import lombok.*;
import org.abrohamovich.littleshop.adapter.web.category.CategoryWebResponse;
import org.abrohamovich.littleshop.adapter.web.supplier.SupplierWebResponse;
import org.abrohamovich.littleshop.domain.model.OfferType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferWebResponse {
    private Long id;
    private String name;
    private double price;
    private OfferType type;
    private String description;
    private CategoryWebResponse category;
    private SupplierWebResponse supplier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
