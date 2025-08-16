package org.abrohamovich.littleshop.application.dto.category;

import org.abrohamovich.littleshop.domain.model.Category;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnCategoryResponse() {
        Long id = 1L;
        String name = "Electronics";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Category category = Category.withId(id, name, null, createdAt, updatedAt);

        CategoryResponse categoryResponse = CategoryResponse.toResponse(category);

        assertNotNull(categoryResponse);
        assertEquals(category.getId(), categoryResponse.getId());
        assertEquals(category.getName(), categoryResponse.getName());
        assertEquals(category.getDescription(), categoryResponse.getDescription());
        assertEquals(category.getCreatedAt(), categoryResponse.getCreatedAt());
        assertEquals(category.getUpdatedAt(), categoryResponse.getUpdatedAt());
    }

    @Test
    void toResponse_WithNullCategory_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CategoryResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("Category cannot be null to continue the conversion."));
    }
}
