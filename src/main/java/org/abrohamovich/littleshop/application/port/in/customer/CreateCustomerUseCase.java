package org.abrohamovich.littleshop.application.port.in.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerCreateCommand;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;

public interface CreateCustomerUseCase {
    CustomerResponse save(CustomerCreateCommand command);
}
