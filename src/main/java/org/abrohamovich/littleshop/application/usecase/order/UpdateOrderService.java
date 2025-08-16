package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.order.UpdateOrderUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.abrohamovich.littleshop.domain.model.Order;
import org.abrohamovich.littleshop.domain.model.User;

@RequiredArgsConstructor
public class UpdateOrderService implements UpdateOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public OrderResponse update(Long orderId, OrderUpdateCommand command) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID '" + orderId + "' not found for update."));
        Customer customer = customerRepositoryPort.findById(command.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID '" + command.getCustomerId() + "' not found."));

        order.updateDetails(customer);
        Order savedOrder = orderRepositoryPort.save(order);

        return OrderResponse.toResponse(savedOrder);
    }
}
