package org.abrohamovich.littleshop.application.usecase.customer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.dto.customer.CustomerUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.customer.UpdateCustomerUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.model.Customer;

@RequiredArgsConstructor
public class UpdateCustomerService implements UpdateCustomerUseCase {
    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public CustomerResponse update(Long id, CustomerUpdateCommand command) {
        Customer existingcustomer = customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found for update."));

        if (!existingcustomer.getEmail().equals(command.getEmail())) {
            if (customerRepositoryPort.findByEmail(command.getEmail()).isPresent()) {
                throw new DuplicateEntryException("Customer with email '" + command.getEmail() + "' already exists.");
            }
        }

        if (!existingcustomer.getPhone().equals(command.getPhone())) {
            if (customerRepositoryPort.findByPhone(command.getPhone()).isPresent()) {
                throw new DuplicateEntryException("Customer with phone '" + command.getPhone() + "' already exists.");
            }
        }

        existingcustomer.updateDetails(command.getFirstName(), command.getLastName(), command.getEmail(), command.getPhone(), command.getAddress());

        Customer updatedcustomer = customerRepositoryPort.save(existingcustomer);

        return CustomerResponse.toResponse(updatedcustomer);
    }
}
