package org.abrohamovich.littleshop.application.port.in.category;

import org.abrohamovich.littleshop.application.dto.category.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;

public interface CreateCategoryUseCase {
    CategoryResponse save(CategoryCreateCommand command);
}
