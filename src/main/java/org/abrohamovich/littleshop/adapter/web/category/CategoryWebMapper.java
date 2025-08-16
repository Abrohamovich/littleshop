package org.abrohamovich.littleshop.adapter.web.category;

import org.abrohamovich.littleshop.application.dto.category.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.category.CategoryUpdateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryWebMapper {
    CategoryCreateCommand toCreateCommand(CategoryCreateWebRequest request);
    CategoryUpdateCommand toUpdateCommand(CategoryUpdateWebRequest request);
    CategoryWebResponse toWebResponse(CategoryResponse response);
}
