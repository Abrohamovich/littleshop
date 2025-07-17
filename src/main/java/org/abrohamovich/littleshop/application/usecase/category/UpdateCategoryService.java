package org.abrohamovich.littleshop.application.usecase.category;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.category.UpdateCategoryUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;

@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public CategoryResponse update(Long id, CategoryUpdateCommand command) {
        Category existingCategory = categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found for update."));

        if (!existingCategory.getName().equals(command.getName())) {
            if (categoryRepositoryPort.findByName(command.getName()).isPresent()) {
                throw new DuplicateEntryException("Category with name '" + command.getName() + "' already exists.");
            }
        }

        existingCategory.updateDetails(command.getName(), command.getDescription());

        Category updatedCategory = categoryRepositoryPort.save(existingCategory);

        return CategoryResponse.toResponse(updatedCategory);
    }
}
