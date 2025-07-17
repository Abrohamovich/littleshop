package org.abrohamovich.littleshop.application.port.in.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetSupplierUseCase {
    SupplierResponse getSupplierById(Long id);
    Page<SupplierResponse> getAllSuppliers(Pageable pageable);
    Page<SupplierResponse> getSuppliersByNameLike(String name, Pageable pageable);
    Page<SupplierResponse> getSuppliersByEmailLike(String email, Pageable pageable);
    Page<SupplierResponse> getSuppliersByPhoneLike(String phone, Pageable pageable);
}
