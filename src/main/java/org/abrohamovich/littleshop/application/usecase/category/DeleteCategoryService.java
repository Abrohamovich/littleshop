package org.abrohamovich.littleshop.application.usecase.category;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.category.DeleteCategoryUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;

@RequiredArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public void deleteById(Long id) {
        if (categoryRepositoryPort.findById(id).isEmpty()) {
            throw new CategoryNotFoundException("Category with ID '" + id + "' not found for deletion.");
        }

        categoryRepositoryPort.deleteById(id);
    }
}
