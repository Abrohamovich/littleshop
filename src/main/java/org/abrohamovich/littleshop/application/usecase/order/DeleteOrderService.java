package org.abrohamovich.littleshop.application.usecase.order;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.order.DeleteOrderUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.order.OrderNotFoundException;

@RequiredArgsConstructor
public class DeleteOrderService implements DeleteOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public void deleteById(Long id) {
        if (orderRepositoryPort.findById(id).isEmpty()) {
            throw new OrderNotFoundException("Order with ID '" + id + "' not found for deletion.");
        }

        orderRepositoryPort.deleteById(id);
    }
}
