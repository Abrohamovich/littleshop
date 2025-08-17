package org.abrohamovich.littleshop.adapter.persistence.jpa.repository;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CustomerJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerJpaEntity, Long> {
    Optional<CustomerJpaEntity> findByEmail(String email);

    Optional<CustomerJpaEntity> findByPhone(String phone);

    Page<CustomerJpaEntity> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    Page<CustomerJpaEntity> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    Page<CustomerJpaEntity> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
