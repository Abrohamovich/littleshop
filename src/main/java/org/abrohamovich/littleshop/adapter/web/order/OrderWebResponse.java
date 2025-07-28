package org.abrohamovich.littleshop.adapter.web.order;

import lombok.*;
import org.abrohamovich.littleshop.adapter.web.customer.CustomerWebResponse;
import org.abrohamovich.littleshop.adapter.web.user.UserWebResponse;
import org.abrohamovich.littleshop.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderWebResponse {
    private Long id;
    private CustomerWebResponse customer;
    private UserWebResponse user;
    private OrderStatus status;
    private List<OrderItemWebResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
