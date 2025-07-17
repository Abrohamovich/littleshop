package org.abrohamovich.littleshop.application.usecase.customer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.customer.CustomerCreateCommand;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.port.in.customer.CreateCustomerUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.Customer;

@RequiredArgsConstructor
public class CreateCustomerService implements CreateCustomerUseCase {
    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public CustomerResponse save(CustomerCreateCommand command) {
        if (customerRepositoryPort.findByEmail(command.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Customer with email " + command.getEmail() + " already exists");
        }
        if (customerRepositoryPort.findByPhone(command.getPhone()).isPresent()) {
            throw new DuplicateEntryException("Customer with phone " + command.getPhone() + " already exists");
        }

        Customer customer = Customer.createNewCustomer(command.getFirstName(), command.getLastName(),
                command.getEmail(), command.getPhone(), command.getAddress());
        Customer savedCustomer = customerRepositoryPort.save(customer);

        return CustomerResponse.toResponse(savedCustomer);
    }
}
