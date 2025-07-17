package org.abrohamovich.littleshop.application.port.in.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCustomerUseCase {
    CustomerResponse findById(Long id);

    Page<CustomerResponse> findAll(Pageable pageable);

    Page<CustomerResponse> findByFirstNameLike(String firstName, Pageable pageable);

    Page<CustomerResponse> findByLastNameLike(String lastName, Pageable pageable);

    Page<CustomerResponse> findByEmailLike(String email, Pageable pageable);
}
