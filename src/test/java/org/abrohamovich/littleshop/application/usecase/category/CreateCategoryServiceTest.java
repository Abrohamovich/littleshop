package org.abrohamovich.littleshop.application.usecase.category;

import org.abrohamovich.littleshop.application.dto.category.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryServiceTest {
    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;
    @InjectMocks
    private CreateCategoryService createCategoryService;

    @Test
    void save_shouldReturnCategoryResponse_whenCategoryNameIsUnique() {
        String categoryName = "Electronics";
        String categoryDescription = "Electronic devices and accessories";
        CategoryCreateCommand command = new CategoryCreateCommand(categoryName, categoryDescription);

        when(categoryRepositoryPort.findByName(categoryName)).thenReturn(Optional.empty());

        Category newCategory = Category.createNew(categoryName, categoryDescription);
        when(categoryRepositoryPort.save(any(Category.class))).thenReturn(newCategory);

        CategoryResponse response = createCategoryService.save(command);

        verify(categoryRepositoryPort).findByName(categoryName);

        verify(categoryRepositoryPort).save(any(Category.class));

        assertEquals(categoryName, response.getName());
        assertEquals(categoryDescription, response.getDescription());
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenCategoryNameExists() {
        String categoryName = "Electronics";
        String categoryDescription = "Electronic devices and accessories";
        CategoryCreateCommand command = new CategoryCreateCommand(categoryName, categoryDescription);

        Category existingCategory = Category.createNew(categoryName, "Old description");

        when(categoryRepositoryPort.findByName(categoryName)).thenReturn(Optional.of(existingCategory));

        DuplicateEntryException ex = assertThrows(DuplicateEntryException.class, () -> {
            createCategoryService.save(command);
        });

        assertTrue(ex.getMessage().startsWith("Category with name '" + categoryName + "' already exists."));

        verify(categoryRepositoryPort, never()).save(any(Category.class));
    }
}