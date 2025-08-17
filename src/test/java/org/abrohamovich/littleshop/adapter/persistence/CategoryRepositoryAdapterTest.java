package org.abrohamovich.littleshop.adapter.persistence;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CategoryJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataCategoryRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.CategoryJpaMapper;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryAdapterTest {
    @Mock
    private SpringDataCategoryRepository springDataCategoryRepository;
    @Mock
    private CategoryJpaMapper categoryJpaMapper;
    @InjectMocks
    private CategoryRepositoryAdapter categoryRepositoryAdapter;

    private Category testCategory;
    private CategoryJpaEntity testEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testCategory = Category.withId(1L, "Test Category", "Description for test category", LocalDateTime.now(), LocalDateTime.now());
        testEntity = new CategoryJpaEntity(1L, "Test Category", "Description for test category", LocalDateTime.now(), LocalDateTime.now());
        pageable = Pageable.unpaged();
    }

    @Test
    void save_shouldReturnSavedCategory_whenSaveIsSuccessful() {
        when(categoryJpaMapper.toJpaEntity(any(Category.class))).thenReturn(testEntity);
        when(springDataCategoryRepository.save(any(CategoryJpaEntity.class))).thenReturn(testEntity);
        when(categoryJpaMapper.toDomainEntity(any(CategoryJpaEntity.class))).thenReturn(testCategory);

        Category savedCategory = categoryRepositoryAdapter.save(testCategory);

        assertNotNull(savedCategory);
        assertEquals(testCategory.getId(), savedCategory.getId());
        assertEquals(testCategory.getName(), savedCategory.getName());
        verify(springDataCategoryRepository).save(testEntity);
        verify(categoryJpaMapper).toDomainEntity(testEntity);
    }

    @Test
    void save_shouldThrowDataPersistenceException_onDataAccessException() {
        when(categoryJpaMapper.toJpaEntity(any(Category.class))).thenReturn(testEntity);
        when(springDataCategoryRepository.save(any(CategoryJpaEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate key"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> categoryRepositoryAdapter.save(testCategory));
        assertTrue(exception.getMessage().contains("Failed to save category due to data integrity violation."));
    }

    @Test
    void save_shouldThrowDataPersistenceException_onGenericException() {
        when(categoryJpaMapper.toJpaEntity(any(Category.class))).thenThrow(new RuntimeException("Mapper error"));

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> categoryRepositoryAdapter.save(testCategory));
        assertTrue(exception.getMessage().contains("Failed to save category."));
    }

    @Test
    void findById_shouldReturnCategory_whenFound() {
        when(springDataCategoryRepository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(categoryJpaMapper.toDomainEntity(testEntity)).thenReturn(testCategory);

        Optional<Category> foundCategory = categoryRepositoryAdapter.findById(1L);

        assertTrue(foundCategory.isPresent());
        assertEquals(testCategory.getId(), foundCategory.get().getId());
        verify(springDataCategoryRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataCategoryRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryRepositoryAdapter.findById(99L);

        assertFalse(foundCategory.isPresent());
    }

    @Test
    void findByName_shouldReturnCategory_whenFound() {
        when(springDataCategoryRepository.findByName("Test Category")).thenReturn(Optional.of(testEntity));
        when(categoryJpaMapper.toDomainEntity(testEntity)).thenReturn(testCategory);

        Optional<Category> foundCategory = categoryRepositoryAdapter.findByName("Test Category");

        assertTrue(foundCategory.isPresent());
        assertEquals(testCategory.getName(), foundCategory.get().getName());
    }

    @Test
    void findByName_shouldReturnEmptyOptional_whenNotFound() {
        when(springDataCategoryRepository.findByName("Non-existent")).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryRepositoryAdapter.findByName("Non-existent");

        assertFalse(foundCategory.isPresent());
    }

    @Test
    void findAll_shouldReturnPageOfCategories() {
        Page<CategoryJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        Page<Category> expectedPage = new PageImpl<>(Collections.singletonList(testCategory));
        when(springDataCategoryRepository.findAll(pageable)).thenReturn(entityPage);
        when(categoryJpaMapper.toDomainEntity(testEntity)).thenReturn(testCategory);

        Page<Category> resultPage = categoryRepositoryAdapter.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCategory.getName(), resultPage.getContent().get(0).getName());
    }

    @Test
    void findByNameLike_shouldReturnPageOfCategories() {
        Page<CategoryJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        Page<Category> expectedPage = new PageImpl<>(Collections.singletonList(testCategory));
        when(springDataCategoryRepository.findByNameContainingIgnoreCase("test", pageable)).thenReturn(entityPage);
        when(categoryJpaMapper.toDomainEntity(testEntity)).thenReturn(testCategory);

        Page<Category> resultPage = categoryRepositoryAdapter.findByNameLike("test", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCategory.getName(), resultPage.getContent().get(0).getName());
    }

    @Test
    void findByDescriptionLike_shouldReturnPageOfCategories() {
        Page<CategoryJpaEntity> entityPage = new PageImpl<>(Collections.singletonList(testEntity));
        Page<Category> expectedPage = new PageImpl<>(Collections.singletonList(testCategory));
        when(springDataCategoryRepository.findByDescriptionContainingIgnoreCase("description", pageable)).thenReturn(entityPage);
        when(categoryJpaMapper.toDomainEntity(testEntity)).thenReturn(testCategory);

        Page<Category> resultPage = categoryRepositoryAdapter.findByDescriptionLike("description", pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(testCategory.getName(), resultPage.getContent().get(0).getName());
    }

    @Test
    void deleteById_shouldSucceed_whenCategoryExists() {
        doNothing().when(springDataCategoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> categoryRepositoryAdapter.deleteById(1L));

        verify(springDataCategoryRepository).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowDataPersistenceException_onException() {
        doThrow(new RuntimeException("DB error")).when(springDataCategoryRepository).deleteById(1L);

        DataPersistenceException exception = assertThrows(DataPersistenceException.class, () -> categoryRepositoryAdapter.deleteById(1L));
        assertTrue(exception.getMessage().contains("Failed to delete category with ID '1'."));
    }
}