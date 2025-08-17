package org.abrohamovich.littleshop.application.usecase.category;

import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCategoryServiceTest {
    private final Long categoryId = 1L;
    private final String categoryName = "Electronics";
    private final String categoryDescription = "Electronic devices";
    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;
    @InjectMocks
    private GetCategoryService getCategoryService;
    private Category testCategory;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testCategory = Category.withId(categoryId, categoryName, categoryDescription, LocalDateTime.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_shouldReturnCategoryResponse_whenCategoryExists() {
        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.of(testCategory));

        CategoryResponse response = getCategoryService.findById(categoryId);

        assertNotNull(response);
        assertEquals(categoryName, response.getName());
        assertEquals(categoryDescription, response.getDescription());
        verify(categoryRepositoryPort).findById(categoryId);
    }

    @Test
    void findById_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> getCategoryService.findById(categoryId));
        verify(categoryRepositoryPort).findById(categoryId);
    }

    @Test
    void findAll_shouldReturnPageOfCategoryResponses_whenCategoriesExist() {
        Page<Category> categoryPage = new PageImpl<>(List.of(testCategory), pageable, 1);
        when(categoryRepositoryPort.findAll(pageable)).thenReturn(categoryPage);

        Page<CategoryResponse> responsePage = getCategoryService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(categoryName, responsePage.getContent().get(0).getName());
        verify(categoryRepositoryPort).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoCategoriesExist() {
        Page<Category> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(categoryRepositoryPort.findAll(pageable)).thenReturn(emptyPage);

        Page<CategoryResponse> responsePage = getCategoryService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(categoryRepositoryPort).findAll(pageable);
    }

    @Test
    void findByNameLike_shouldReturnPageOfCategoryResponses_whenMatchesExist() {
        String searchTerm = "Electr";
        Page<Category> categoryPage = new PageImpl<>(List.of(testCategory), pageable, 1);
        when(categoryRepositoryPort.findByNameLike(searchTerm, pageable)).thenReturn(categoryPage);

        Page<CategoryResponse> responsePage = getCategoryService.findByNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(categoryName, responsePage.getContent().get(0).getName());
        verify(categoryRepositoryPort).findByNameLike(searchTerm, pageable);
    }

    @Test
    void findByNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Category> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(categoryRepositoryPort.findByNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<CategoryResponse> responsePage = getCategoryService.findByNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(categoryRepositoryPort).findByNameLike(searchTerm, pageable);
    }

    @Test
    void findByDescriptionLike_shouldReturnPageOfCategoryResponses_whenMatchesExist() {
        String searchTerm = "devices";
        Page<Category> categoryPage = new PageImpl<>(List.of(testCategory), pageable, 1);
        when(categoryRepositoryPort.findByDescriptionLike(searchTerm, pageable)).thenReturn(categoryPage);

        Page<CategoryResponse> responsePage = getCategoryService.findByDescriptionLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(categoryDescription, responsePage.getContent().get(0).getDescription());
        verify(categoryRepositoryPort).findByDescriptionLike(searchTerm, pageable);
    }

    @Test
    void findByDescriptionLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Category> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(categoryRepositoryPort.findByDescriptionLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<CategoryResponse> responsePage = getCategoryService.findByDescriptionLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(categoryRepositoryPort).findByDescriptionLike(searchTerm, pageable);
    }

}