package org.abrohamovich.littleshop.application.port.in.category;

import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryUpdateCommand;

public interface UpdateCategoryUseCase {
    CategoryResponse updateCategory(Long id, CategoryUpdateCommand command);
}
