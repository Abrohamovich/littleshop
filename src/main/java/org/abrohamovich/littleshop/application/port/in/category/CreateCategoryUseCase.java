package org.abrohamovich.littleshop.application.port.in.category;

import org.abrohamovich.littleshop.application.dto.cateogry.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;

public interface CreateCategoryUseCase {
    CategoryResponse createCategory(CategoryCreateCommand command);
}
