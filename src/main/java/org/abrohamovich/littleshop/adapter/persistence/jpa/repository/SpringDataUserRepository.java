package org.abrohamovich.littleshop.adapter.persistence.jpa.repository;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity,Long> {
    Optional<UserJpaEntity> findByEmail(String email);

    Optional<UserJpaEntity> findByPhone(String phone);

    Page<UserJpaEntity> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    Page<UserJpaEntity> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    Page<UserJpaEntity> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
