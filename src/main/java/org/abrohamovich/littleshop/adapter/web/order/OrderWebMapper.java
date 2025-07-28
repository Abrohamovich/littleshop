package org.abrohamovich.littleshop.adapter.web.order;

import org.abrohamovich.littleshop.application.dto.order.OrderCreateCommand;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateCommand;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateStatusCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {
    OrderCreateCommand toCreateCommand(OrderCreateWebRequest request);
    OrderUpdateStatusCommand toUpdateStatusCommand(OrderUpdateStatusCommand request);
    OrderUpdateCommand toUpdateCommand(OrderUpdateWebRequest request);
    OrderWebResponse toWebResponse(OrderResponse response);
}
