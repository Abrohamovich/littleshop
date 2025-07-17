package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderCreateCommand;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.port.in.order.CreateOrderUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.abrohamovich.littleshop.domain.model.*;

import java.util.List;

@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    private final OfferRepositoryPort offerRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public OrderResponse save(OrderCreateCommand command) {
        List<OrderItem> items = command.getItems().stream()
                .map(orderCommand -> {
                    Offer offer = offerRepositoryPort.findById(orderCommand.getOfferId())
                            .orElseThrow(() -> new OfferNotFoundException("Offer with ID '" + orderCommand.getOfferId() + "' not found."));
                    return OrderItem.createNew(offer, orderCommand.getQuantity());
                }).toList();

        Customer customer = customerRepositoryPort.findById(command.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID '" + command.getCustomerId() + "' not found."));
        User user = userRepositoryPort.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + command.getUserId() + "' not found."));

        Order order = Order.createNew(customer, user, items);
        Order savedOrder = orderRepositoryPort.save(order);

        return OrderResponse.toResponse(savedOrder);
    }
}
