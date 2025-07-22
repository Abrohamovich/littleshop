package org.abrohamovich.littleshop.adapter.web.supplier;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierWebResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
