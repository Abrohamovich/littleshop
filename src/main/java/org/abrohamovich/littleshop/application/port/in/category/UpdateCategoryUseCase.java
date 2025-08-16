package org.abrohamovich.littleshop.application.port.in.category;

import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.category.CategoryUpdateCommand;

public interface UpdateCategoryUseCase {
    CategoryResponse update(Long id, CategoryUpdateCommand command);
}
