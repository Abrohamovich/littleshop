package org.abrohamovich.littleshop.application.dto.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateCommand {
    private Long customerId;
}
