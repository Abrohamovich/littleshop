package org.abrohamovich.littleshop.application.dto.category;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.Category;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null to continue the conversion.");
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
