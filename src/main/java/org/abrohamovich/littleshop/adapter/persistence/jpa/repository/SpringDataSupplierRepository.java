package org.abrohamovich.littleshop.adapter.persistence.jpa.repository;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.SupplierJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataSupplierRepository extends JpaRepository<SupplierJpaEntity, Long> {
    Optional<SupplierJpaEntity> findByName(String name);

    Optional<SupplierJpaEntity> findByEmail(String email);

    Optional<SupplierJpaEntity> findByPhone(String phone);

    Page<SupplierJpaEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<SupplierJpaEntity> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    Page<SupplierJpaEntity> findByPhoneContainingIgnoreCase(String phone, Pageable pageable);
}

