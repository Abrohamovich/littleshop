package org.abrohamovich.littleshop.adapter.web.offer;

import lombok.*;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
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
    private CategoryResponse category;
    private SupplierResponse supplier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
