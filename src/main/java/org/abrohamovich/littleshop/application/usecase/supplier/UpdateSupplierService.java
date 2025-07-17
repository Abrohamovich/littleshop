package org.abrohamovich.littleshop.application.usecase.supplier;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.supplier.UpdateSupplierUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
import org.abrohamovich.littleshop.domain.model.Supplier;

@RequiredArgsConstructor
public class UpdateSupplierService implements UpdateSupplierUseCase {
    private final SupplierRepositoryPort supplierRepositoryPort;

    @Override
    public SupplierResponse update(Long id, SupplierUpdateCommand command) {
        Supplier existingSupplier = supplierRepositoryPort.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with ID " + id + " not found for update."));

        if (!existingSupplier.getName().equals(command.getName())) {
            if (supplierRepositoryPort.findByName(command.getName()).isPresent()) {
                throw new DuplicateEntryException("Supplier with name '" + command.getEmail() + "' already exists.");
            }
        }
        if (!existingSupplier.getEmail().equals(command.getEmail())) {
            if (supplierRepositoryPort.findByEmail(command.getEmail()).isPresent()) {
                throw new DuplicateEntryException("Supplier with email '" + command.getEmail() + "' already exists.");
            }
        }
        if (!existingSupplier.getPhone().equals(command.getPhone())) {
            if (supplierRepositoryPort.findByPhone(command.getPhone()).isPresent()) {
                throw new DuplicateEntryException("Supplier with phone '" + command.getEmail() + "' already exists.");
            }
        }

        existingSupplier.updateDetails(command.getName(), command.getEmail(),
                command.getPhone(), command.getAddress(), command.getDescription());

        Supplier updatedSupplier = supplierRepositoryPort.save(existingSupplier);

        return SupplierResponse.toResponse(updatedSupplier);
    }
}
