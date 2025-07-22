package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.SupplierJpaEntity;
import org.abrohamovich.littleshop.domain.model.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SupplierJpaMapper {
    @Mapping(target = "id", source = "id")
    public abstract SupplierJpaEntity toJpaEntity(Supplier domainSupplier);

    public abstract Supplier toDomainEntity(SupplierJpaEntity jpaEntity);

    @ObjectFactory
    public Supplier createSupplierFromEntity(SupplierJpaEntity entity) {
        if (entity.getId() != null) {
            return Supplier.withId(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getAddress(),
                    entity.getDescription(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        } else {
            return Supplier.createNew(
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getAddress(),
                    entity.getDescription()
            );
        }
    }

    public abstract void updateJpaEntityFromDomain(Supplier domainSupplier, @MappingTarget SupplierJpaEntity jpaEntity);
}
