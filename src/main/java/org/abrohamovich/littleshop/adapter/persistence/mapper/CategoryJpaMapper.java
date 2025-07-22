package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CategoryJpaEntity;
import org.abrohamovich.littleshop.domain.model.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CategoryJpaMapper {
    @Mapping(target = "id", source = "id")
    public abstract CategoryJpaEntity toJpaEntity(Category domainCategory);

    public abstract Category toDomainEntity(CategoryJpaEntity jpaEntity);

    @ObjectFactory
    public Category createCategoryFromEntity(CategoryJpaEntity entity) {
        if (entity.getId() != null) {
            return Category.withId(entity.getId(), entity.getName(), entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
        } else {
            return Category.createNew(entity.getName(), entity.getDescription());
        }
    }

    public abstract void updateJpaEntityFromDomain(Category domainCategory, @MappingTarget CategoryJpaEntity jpaEntity);
}
