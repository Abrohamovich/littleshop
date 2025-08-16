package org.abrohamovich.littleshop.application.dto.cateogry;

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
