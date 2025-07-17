package org.abrohamovich.littleshop.application.port.in.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetSupplierUseCase {
    SupplierResponse findById(Long id);
    Page<SupplierResponse> findAll(Pageable pageable);
    Page<SupplierResponse> findByNameLike(String name, Pageable pageable);
    Page<SupplierResponse> findByEmailLike(String email, Pageable pageable);
    Page<SupplierResponse> findByPhoneLike(String phone, Pageable pageable);
}
