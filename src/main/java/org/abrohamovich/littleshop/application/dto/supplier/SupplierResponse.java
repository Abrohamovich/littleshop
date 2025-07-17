package org.abrohamovich.littleshop.application.dto.supplier;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.Supplier;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SupplierResponse toResponse(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null to continue the conversion.");
        }
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .description(supplier.getDescription())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }
}
