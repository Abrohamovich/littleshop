package org.abrohamovich.littleshop.application.usecase.supplier;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierCreateCommand;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.port.in.supplier.CreateSupplierUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.Supplier;

@RequiredArgsConstructor
public class CreateSupplierService implements CreateSupplierUseCase {
    private final SupplierRepositoryPort supplierRepositoryPort;

    @Override
    public SupplierResponse save(SupplierCreateCommand command) {
        if (supplierRepositoryPort.findByEmail(command.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Customer with email '" + command.getEmail() + "' already exists.");
        }
        if (supplierRepositoryPort.findByName(command.getName()).isPresent()) {
            throw new DuplicateEntryException("Customer with name '" + command.getEmail() + "' already exists.");
        }
        if (supplierRepositoryPort.findByPhone(command.getPhone()).isPresent()) {
            throw new DuplicateEntryException("Customer with phone '" + command.getEmail() + "' already exists.");
        }

        Supplier supplier = Supplier.createNew(command.getName(), command.getEmail(),
                command.getPhone(), command.getAddress(), command.getDescription());
        Supplier savedSupplier = supplierRepositoryPort.save(supplier);

        return SupplierResponse.toResponse(savedSupplier);
    }
}
