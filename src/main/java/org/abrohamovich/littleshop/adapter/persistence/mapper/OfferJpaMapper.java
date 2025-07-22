package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.OfferJpaEntity;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OfferJpaMapper {
    @Autowired
    protected CategoryJpaMapper categoryJpaMapper;
    @Autowired
    protected SupplierJpaMapper supplierJpaMapper;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "category", expression = "java(categoryJpaMapper.toJpaEntity(domainOffer.getCategory()))")
    @Mapping(target = "supplier", expression = "java(supplierJpaMapper.toJpaEntity(domainOffer.getSupplier()))")
    public abstract OfferJpaEntity toJpaEntity(Offer domainOffer);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    public abstract Offer toDomainEntity(OfferJpaEntity jpaEntity);

    @ObjectFactory
    public Offer createOfferFromEntity(OfferJpaEntity entity) {
        return Offer.createForPersistenceHydration(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getType(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category", expression = "java(categoryJpaMapper.toJpaEntity(domainOffer.getCategory()))")
    @Mapping(target = "supplier", expression = "java(supplierJpaMapper.toJpaEntity(domainOffer.getSupplier()))")
    public abstract void updateJpaEntityFromDomain(Offer domainOffer, @MappingTarget OfferJpaEntity jpaEntity);
}
