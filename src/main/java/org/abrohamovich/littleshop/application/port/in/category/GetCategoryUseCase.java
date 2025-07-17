package org.abrohamovich.littleshop.application.port.in.category;

import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCategoryUseCase {
    CategoryResponse findById(Long id);

    Page<CategoryResponse> findAll(Pageable pageable);

    Page<CategoryResponse> findByNameLike(String name, Pageable pageable);

    Page<CategoryResponse> findByDescriptionLike(String description, Pageable pageable);
}
