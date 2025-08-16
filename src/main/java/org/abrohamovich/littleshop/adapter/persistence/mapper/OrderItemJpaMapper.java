package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.OrderItemJpaEntity;
import org.abrohamovich.littleshop.domain.model.OrderItem;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderItemJpaMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "order", ignore = true)
    public abstract OrderItemJpaEntity toJpaEntity(OrderItem domainOrderItem);

    @Mapping(target = "offer", ignore = true)
    public abstract OrderItem toDomainEntity(OrderItemJpaEntity jpaEntity);

    @ObjectFactory
    public OrderItem createOrderItemFromEntity(OrderItemJpaEntity entity) {
        if (entity.getId() != null) {
            return OrderItem.createForPersistenceHydration(
                    entity.getId(),
                    entity.getQuantity(),
                    entity.getPriceAtTimeOfOrder(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        } else {
            throw new UnsupportedOperationException("OrderItem createNew is handled by the adapter layer (OrderJpaAdapter).");
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract void updateJpaEntityFromDomain(OrderItem domainOrderItem, @MappingTarget OrderItemJpaEntity jpaEntity);
}
