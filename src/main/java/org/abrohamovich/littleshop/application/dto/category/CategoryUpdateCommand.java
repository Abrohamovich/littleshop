package org.abrohamovich.littleshop.application.dto.category;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateCommand {
    private String name;
    private String description;
}
