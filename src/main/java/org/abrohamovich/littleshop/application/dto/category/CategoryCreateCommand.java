package org.abrohamovich.littleshop.application.dto.category;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateCommand {
    private String name;
    private String description;
}
