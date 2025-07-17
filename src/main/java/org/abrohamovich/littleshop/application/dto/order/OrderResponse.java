package org.abrohamovich.littleshop.application.dto.order;

import lombok.*;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemResponse;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.domain.model.Order;
import org.abrohamovich.littleshop.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private CustomerResponse customer;
    private UserResponse user;
    private OrderStatus status;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse toResponse(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null to continue the conversion.");
        }
        return OrderResponse.builder()
                .id(order.getId())
                .customer(CustomerResponse.toResponse(order.getCustomer()))
                .user(UserResponse.toResponse(order.getUser()))
                .status(order.getStatus())
                .items(order.getItems().stream().map(OrderItemResponse::toResponse).toList())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
