package org.abrohamovich.littleshop.adapter.persistence.jpa.repository;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CategoryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryJpaEntity, Long> {
    Optional<CategoryJpaEntity> findByName(String name);

    Page<CategoryJpaEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<CategoryJpaEntity> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
