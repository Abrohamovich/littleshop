package org.abrohamovich.littleshop.application.port.in.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCustomerUseCase {
    CustomerResponse getCustomerById(Long id);
    Page<CustomerResponse> getAllCustomers(Pageable pageable);
    Page<CustomerResponse> getByFirstNameLike(String firstName, Pageable pageable);
    Page<CustomerResponse> getByLastNameLike(String lastName, Pageable pageable);
    Page<CustomerResponse> getByEmailLike(String email, Pageable pageable);
}
