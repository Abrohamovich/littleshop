package org.abrohamovich.littleshop.application.usecase.category;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.abrohamovich.littleshop.application.port.in.category.CreateCategoryUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.Category;

@RequiredArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public CategoryResponse save(CategoryCreateCommand command) {
        if (categoryRepositoryPort.findByName(command.getName()).isPresent()) {
            throw new DuplicateEntryException("Category with name '" + command.getName() + "' already exists.");
        }

        Category category = Category.createNew(command.getName(), command.getDescription());
        Category savedCategory = categoryRepositoryPort.save(category);

        return CategoryResponse.toResponse(savedCategory);
    }
}
