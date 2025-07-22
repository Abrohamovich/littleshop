package org.abrohamovich.littleshop.adapter.persistence.mapper;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CustomerJpaEntity;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CustomerJpaMapper {
    @Mapping(target = "id", source = "id")
    public abstract CustomerJpaEntity toJpaEntity(Customer domainCustomer);

    public abstract Customer toDomainEntity(CustomerJpaEntity jpaEntity);

    @ObjectFactory
    public Customer createCustomerFromEntity(CustomerJpaEntity entity) {
        if (entity.getId() != null) {
            return Customer.withId(
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getAddress(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        } else {
            return Customer.createNewCustomer(
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getAddress()
            );
        }
    }

    public abstract void updateJpaEntityFromDomain(Customer domainCustomer, @MappingTarget CustomerJpaEntity jpaEntity);
}
