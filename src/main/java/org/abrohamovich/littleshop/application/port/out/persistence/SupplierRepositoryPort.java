package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SupplierRepositoryPort {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(Long id);
    Optional<Supplier> findByName(String name);
    Optional<Supplier> findByEmail(String email);
    Optional<Supplier> findByPhone(String phone);
    Page<Supplier> findAll(Pageable pageable);
    Page<Supplier> findByNameLike(String name, Pageable pageable);
    Page<Supplier> findByEmailLike(String email, Pageable pageable);
    Page<Supplier> findByPhoneLike(String phone, Pageable pageable);
    void deleteById(Long id);
}
