package org.abrohamovich.littleshop.application.dto.customer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateCommand {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
