package org.abrohamovich.littleshop.adapter.web.order;

import org.abrohamovich.littleshop.application.dto.orderItem.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemWebMapper {
    OrderItemCreateCommand toCreateCommand(OrderItemCreateWebRequest request);
    OrderItemAddToOrderCommand toAddToOrderCommand(OrderItemAddToOrderWebRequest request);
    OrderItemRemoveFromOrderCommand toRemoveFromOrderCommand(OrderItemRemoveFromOrderWebRequest request);
    OrderItemUpdateQuantityCommand toUpdateQuantityCommand(OrderItemUpdateQuantityWebRequest request);
    OrderItemWebResponse toWebResponse(OrderItemResponse response);
}
