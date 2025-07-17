package org.abrohamovich.littleshop.application.port.in.category;

import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCategoryUseCase {
    CategoryResponse getCategoryById(Long id);
    Page<CategoryResponse> getAllCategories(Pageable pageable);
    Page<CategoryResponse> getCategoriesByNameLike(String name, Pageable pageable);
    Page<CategoryResponse> getCategoriesByDescriptionLike(String description, Pageable pageable);
}
