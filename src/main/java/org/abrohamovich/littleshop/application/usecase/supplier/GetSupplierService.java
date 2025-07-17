package org.abrohamovich.littleshop.application.usecase.supplier;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.port.in.supplier.GetSupplierUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GetSupplierService implements GetSupplierUseCase {
    private final SupplierRepositoryPort supplierRepositoryPort;

    @Override
    public SupplierResponse findById(Long id) {
        return supplierRepositoryPort.findById(id)
                .map(SupplierResponse::toResponse)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with ID '" + id + "' not found."));
    }

    @Override
    public Page<SupplierResponse> findAll(Pageable pageable) {
        return supplierRepositoryPort.findAll(pageable)
                .map(SupplierResponse::toResponse);
    }

    @Override
    public Page<SupplierResponse> findByNameLike(String name, Pageable pageable) {
        return supplierRepositoryPort.findByNameLike(name, pageable)
                .map(SupplierResponse::toResponse);
    }

    @Override
    public Page<SupplierResponse> findByEmailLike(String email, Pageable pageable) {
        return supplierRepositoryPort.findByEmailLike(email, pageable)
                .map(SupplierResponse::toResponse);
    }

    @Override
    public Page<SupplierResponse> findByPhoneLike(String phone, Pageable pageable) {
        return supplierRepositoryPort.findByPhoneLike(phone, pageable)
                .map(SupplierResponse::toResponse);
    }
}
