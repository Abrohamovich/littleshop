package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.UserJpaEntity;
import org.abrohamovich.littleshop.domain.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserJpaMapper {
    @Mapping(target = "id", source = "id")
    public abstract UserJpaEntity toJpaEntity(User domainUser);

    public abstract User toDomainEntity(UserJpaEntity jpaEntity);

    @ObjectFactory
    public User createUserFromEntity(UserJpaEntity entity) {
        if (entity.getId() != null) {
            return User.withId(
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getRole(),
                    entity.getPhone(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        } else {
            return User.createNewUser(
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getRole(),
                    entity.getPhone()
            );
        }
    }

    public abstract void updateJpaEntityFromDomain(User domainUser, @MappingTarget UserJpaEntity jpaEntity);
}
