package org.abrohamovich.littleshop.adapter.web.customer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateWebRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
