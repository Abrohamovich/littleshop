package org.abrohamovich.littleshop.application.dto.cateogry;

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
