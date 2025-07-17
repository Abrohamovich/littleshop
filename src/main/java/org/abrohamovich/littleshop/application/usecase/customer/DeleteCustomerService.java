package org.abrohamovich.littleshop.application.usecase.customer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.customer.DeleteCustomerUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;

@RequiredArgsConstructor
public class DeleteCustomerService implements DeleteCustomerUseCase {
    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public void deleteById(Long id) {
        if (customerRepositoryPort.findById(id).isEmpty()) {
            throw new CustomerNotFoundException("Category with ID " + id + " not found for deletion.");
        }

        customerRepositoryPort.deleteById(id);
    }
}
