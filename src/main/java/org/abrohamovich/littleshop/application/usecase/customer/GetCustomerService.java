package org.abrohamovich.littleshop.application.usecase.customer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.port.in.customer.GetCustomerUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GetCustomerService implements GetCustomerUseCase {
    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public CustomerResponse findById(Long id) {
        return customerRepositoryPort.findById(id)
                .map(CustomerResponse::toResponse)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID '" + id + "' not found."));
    }

    @Override
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepositoryPort.findAll(pageable)
                .map(CustomerResponse::toResponse);
    }

    @Override
    public Page<CustomerResponse> findByFirstNameLike(String firstName, Pageable pageable) {
        return customerRepositoryPort.findByFirstNameLike(firstName, pageable)
                .map(CustomerResponse::toResponse);
    }

    @Override
    public Page<CustomerResponse> findByLastNameLike(String lastName, Pageable pageable) {
        return customerRepositoryPort.findByLastNameLike(lastName, pageable)
                .map(CustomerResponse::toResponse);
    }

    @Override
    public Page<CustomerResponse> findByEmailLike(String email, Pageable pageable) {
        return customerRepositoryPort.findByEmailLike(email, pageable)
                .map(CustomerResponse::toResponse);
    }
}
