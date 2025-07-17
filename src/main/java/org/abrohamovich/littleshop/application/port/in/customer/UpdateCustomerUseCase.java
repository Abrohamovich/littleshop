package org.abrohamovich.littleshop.application.port.in.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.dto.customer.CustomerUpdateCommand;

public interface UpdateCustomerUseCase {
    CustomerResponse update(Long id, CustomerUpdateCommand command);
}
