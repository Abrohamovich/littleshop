package org.abrohamovich.littleshop.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CategoryJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataCategoryRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.CategoryJpaMapper;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {
    private final SpringDataCategoryRepository springDataCategoryRepository;
    private final CategoryJpaMapper categoryJpaMapper;

    @Override
    public Category save(Category category) {
        try {
            CategoryJpaEntity entity = categoryJpaMapper.toJpaEntity(category);
            CategoryJpaEntity savedEntity = springDataCategoryRepository.save(entity);
            return categoryJpaMapper.toDomainEntity(savedEntity);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save category due to data integrity violation. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to save category. " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Category> findById(Long id) {
        return springDataCategoryRepository.findById(id)
                .map(categoryJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return springDataCategoryRepository.findByName(name)
                .map(categoryJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return springDataCategoryRepository.findAll(pageable)
                .map(categoryJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Category> findByNameLike(String name, Pageable pageable) {
        return springDataCategoryRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(categoryJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Category> findByDescriptionLike(String description, Pageable pageable) {
        return springDataCategoryRepository.findByDescriptionContainingIgnoreCase(description, pageable)
                .map(categoryJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        try {
            springDataCategoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to delete category with ID '" + id + "'. " + e.getMessage(), e);
        }
    }
}
