package org.abrohamovich.littleshop.adapter.web.category;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWebResponse {
    private Long id;
    private String name;
    private String description;
}
