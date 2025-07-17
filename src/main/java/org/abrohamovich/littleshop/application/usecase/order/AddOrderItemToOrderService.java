package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.orderItem.OrderItemAddToOrderCommand;
import org.abrohamovich.littleshop.application.port.in.order.AddOrderItemToOrderUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.Order;

@RequiredArgsConstructor
public class AddOrderItemToOrderService implements AddOrderItemToOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    private final OfferRepositoryPort offerRepositoryPort;

    @Override
    public OrderResponse add(Long orderId, OrderItemAddToOrderCommand command) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + orderId + "' not found."));
        Offer offer = offerRepositoryPort.findById(command.getOfferId())
                .orElseThrow(() -> new OfferNotFoundException("Offer with ID '" + command.getOfferId() + "' not found."));

        order.addOrderItem(offer, command.getQuantity());

        return OrderResponse.toResponse(order);
    }
}
