package org.abrohamovich.littleshop.application.dto.orderItem;

public class OrderItemUpdateQuantityCommand {
    private Long orderId;
    private Long orderItemId;
    private int newQuantity;
}
