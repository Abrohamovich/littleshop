package org.abrohamovich.littleshop.adapter.web.category;

import org.abrohamovich.littleshop.application.dto.cateogry.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.cateogry.CategoryUpdateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryWebMapper {
    CategoryCreateCommand toCreateCommand(CategoryCreateWebRequest request);
    CategoryUpdateCommand toUpdateCommand(CategoryUpdateWebRequest request);
    CategoryWebResponse toWebResponse(CategoryResponse response);
}
