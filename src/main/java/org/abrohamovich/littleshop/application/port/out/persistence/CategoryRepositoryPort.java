package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByNameLike(String name, Pageable pageable);
    Page<Category> findByDescriptionLike(String description, Pageable pageable);
    void deleteById(Long id);
}
