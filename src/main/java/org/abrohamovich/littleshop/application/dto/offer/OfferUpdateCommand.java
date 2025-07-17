package org.abrohamovich.littleshop.application.dto.offer;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.Category;
import org.abrohamovich.littleshop.domain.model.OfferType;
import org.abrohamovich.littleshop.domain.model.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferUpdateCommand {
    private String name;
    private double price;
    private OfferType type;
    private String description;
    private Long categoryId;
    private Long supplierId;
}
