package org.abrohamovich.littleshop.application.usecase.supplier;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.supplier.DeleteSupplierUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;

@RequiredArgsConstructor
public class DeleteSupplierService implements DeleteSupplierUseCase {
    private final SupplierRepositoryPort supplierRepositoryPort;

    @Override
    public void deleteById(Long id) {
        if (supplierRepositoryPort.findById(id).isEmpty()) {
            throw new SupplierNotFoundException("Supplier with ID '" + id + "' not found for deletion.");
        }

        supplierRepositoryPort.deleteById(id);
    }
}
