package org.abrohamovich.littleshop.application.dto.supplier;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierUpdateCommand {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
