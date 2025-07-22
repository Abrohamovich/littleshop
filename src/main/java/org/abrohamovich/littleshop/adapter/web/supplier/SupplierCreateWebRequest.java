package org.abrohamovich.littleshop.adapter.web.supplier;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCreateWebRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
