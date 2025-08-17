package org.abrohamovich.littleshop.application.usecase.category;

import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.category.CategoryUpdateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryServiceTest {
    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;
    @InjectMocks
    private UpdateCategoryService updateCategoryService;

    @Test
    void update_shouldReturnCategoryResponse_whenUpdatingCategoryWithNewUniqueName() {
        Long categoryId = 1L;
        String oldName = "Electronics";
        String newName = "Gadgets";
        String newDescription = "Cool new gadgets";
        Category existingCategory = Category.withId(categoryId, oldName, "Old description", LocalDateTime.now(), LocalDateTime.now());
        CategoryUpdateCommand command = new CategoryUpdateCommand(newName, newDescription);

        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepositoryPort.findByName(newName)).thenReturn(Optional.empty());
        when(categoryRepositoryPort.save(any(Category.class))).thenReturn(Category.withId(categoryId, newName, newDescription, existingCategory.getCreatedAt(), LocalDateTime.now()));

        CategoryResponse response = updateCategoryService.update(categoryId, command);

        verify(categoryRepositoryPort).findById(categoryId);
        verify(categoryRepositoryPort).findByName(newName);
        verify(categoryRepositoryPort).save(any(Category.class));
        assertEquals(newName, response.getName());
        assertEquals(newDescription, response.getDescription());
    }

    @Test
    void update_shouldReturnCategoryResponse_whenUpdatingCategoryWithSameName() {
        Long categoryId = 1L;
        String oldName = "Electronics";
        String newDescription = "Updated description for electronics";
        Category existingCategory = Category.withId(categoryId, oldName, "Old description", LocalDateTime.now(), LocalDateTime.now());
        CategoryUpdateCommand command = new CategoryUpdateCommand(oldName, newDescription);

        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepositoryPort.save(any(Category.class))).thenReturn(Category.withId(categoryId, oldName, newDescription, existingCategory.getCreatedAt(), LocalDateTime.now()));

        CategoryResponse response = updateCategoryService.update(categoryId, command);

        verify(categoryRepositoryPort).findById(categoryId);
        verify(categoryRepositoryPort, never()).findByName(any());
        verify(categoryRepositoryPort).save(any(Category.class));
        assertEquals(oldName, response.getName());
        assertEquals(newDescription, response.getDescription());
    }

    @Test
    void update_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        Long categoryId = 99L;
        CategoryUpdateCommand command = new CategoryUpdateCommand("Non-existent", "Description");

        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            updateCategoryService.update(categoryId, command);
        });

        verify(categoryRepositoryPort).findById(categoryId);
        verify(categoryRepositoryPort, never()).findByName(any());
        verify(categoryRepositoryPort, never()).save(any());
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNewNameAlreadyExists() {
        Long categoryId = 1L;
        String oldName = "Electronics";
        String newName = "Gadgets";
        String newDescription = "Cool gadgets";
        Category existingCategory = Category.withId(categoryId, oldName, "Old description", LocalDateTime.now(), LocalDateTime.now());
        Category existingDuplicateCategory = Category.withId(2L, newName, "Duplicate description", LocalDateTime.now(), LocalDateTime.now());
        CategoryUpdateCommand command = new CategoryUpdateCommand(newName, newDescription);

        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepositoryPort.findByName(newName)).thenReturn(Optional.of(existingDuplicateCategory));

        assertThrows(DuplicateEntryException.class, () -> {
            updateCategoryService.update(categoryId, command);
        });

        verify(categoryRepositoryPort).findById(categoryId);
        verify(categoryRepositoryPort).findByName(newName);
        verify(categoryRepositoryPort, never()).save(any());
    }

}