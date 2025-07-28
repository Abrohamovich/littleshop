package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.OrderJpaEntity;
import org.abrohamovich.littleshop.domain.model.Order;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderJpaMapper {
    @Autowired
    protected CustomerJpaMapper customerJpaMapper;
    @Autowired
    protected UserJpaMapper userJpaMapper;
    @Autowired
    protected OrderItemJpaMapper orderItemJpaMapper;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customer", expression = "java(customerJpaMapper.toJpaEntity(domainOrder.getCustomer()))")
    @Mapping(target = "user", expression = "java(userJpaMapper.toJpaEntity(domainOrder.getUser()))")
    @Mapping(target = "items", ignore = true)
    public abstract OrderJpaEntity toJpaEntity(Order domainOrder);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    public abstract Order toDomainEntity(OrderJpaEntity jpaEntity);

    @ObjectFactory
    public Order createOrderFromEntity(OrderJpaEntity entity) {
        if (entity.getId() != null) {
            return Order.createForPersistenceHydration(
                    entity.getId(),
                    entity.getCreatedAt(),
                    entity.getStatus(),
                    entity.getUpdatedAt()
            );
        } else {
            throw new UnsupportedOperationException("Order createNew should be handled directly in the OrderJpaAdapter.");
        }
    }

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    public abstract void updateJpaEntityFromDomain(Order domainOrder, @MappingTarget OrderJpaEntity jpaEntity);
}
