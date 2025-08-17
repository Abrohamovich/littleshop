package org.abrohamovich.littleshop.application.usecase.category;

import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryServiceTest {

    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;

    @InjectMocks
    private DeleteCategoryService deleteCategoryService;

    @Test
    void deleteById_shouldDeleteCategory_whenCategoryExists() {
        Category category = Category.withId(1L, "Electronics", "Schemes, etc.",
                LocalDateTime.now(), LocalDateTime.now());

        when(categoryRepositoryPort.findById(1L)).thenReturn(Optional.of(category));

        deleteCategoryService.deleteById(1L);

        verify(categoryRepositoryPort).findById(1L);
        verify(categoryRepositoryPort).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        Long categoryId = 99L;

        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> deleteCategoryService.deleteById(categoryId));

        verify(categoryRepositoryPort).findById(categoryId);
        verify(categoryRepositoryPort, never()).deleteById(categoryId);
    }
}
