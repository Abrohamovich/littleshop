package org.abrohamovich.littleshop.application.usecase.category;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.abrohamovich.littleshop.application.port.in.category.GetCategoryUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GetCategoryService implements GetCategoryUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepositoryPort.findById(id)
                .map(CategoryResponse::toResponse)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID '" + id + "' not found."));
    }

    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepositoryPort.findAll(pageable)
                .map(CategoryResponse::toResponse);
    }

    @Override
    public Page<CategoryResponse> findByNameLike(String name, Pageable pageable) {
        return categoryRepositoryPort.findByNameLike(name, pageable)
                .map(CategoryResponse::toResponse);
    }

    @Override
    public Page<CategoryResponse> findByDescriptionLike(String description, Pageable pageable) {
        return categoryRepositoryPort.findByDescriptionLike(description, pageable)
                .map(CategoryResponse::toResponse);
    }
}
