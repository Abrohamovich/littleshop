package org.abrohamovich.littleshop.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.*;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataOrderRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.CustomerJpaMapper;
import org.abrohamovich.littleshop.adapter.persistence.mapper.OfferJpaMapper;
import org.abrohamovich.littleshop.adapter.persistence.mapper.OrderItemJpaMapper;
import org.abrohamovich.littleshop.adapter.persistence.mapper.OrderJpaMapper;
import org.abrohamovich.littleshop.adapter.persistence.mapper.UserJpaMapper;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OrderRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.Order;
import org.abrohamovich.littleshop.domain.model.OrderItem;
import org.abrohamovich.littleshop.domain.model.OrderStatus;
import org.abrohamovich.littleshop.domain.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {
    private final SpringDataOrderRepository springDataOrderRepository;
    private final UserJpaMapper userJpaMapper;
    private final CustomerJpaMapper customerJpaMapper;
    private final OfferJpaMapper offerJpaMapper;
    private final OrderItemJpaMapper orderItemJpaMapper;
    private final OrderJpaMapper orderJpaMapper;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final OfferRepositoryPort offerRepositoryPort;

    @Override
    @Transactional
    public Order save(Order order) {
        try {
            CustomerJpaEntity customerJpaEntity = customerRepositoryPort.findById(order.getCustomer().getId())
                    .map(customerJpaMapper::toJpaEntity)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + order.getCustomer().getId()));

            UserJpaEntity userJpaEntity = userRepositoryPort.findById(order.getUser().getId())
                    .map(userJpaMapper::toJpaEntity)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + order.getUser().getId()));

            OrderJpaEntity orderJpaEntity;

            if (order.getId() == null) {
                orderJpaEntity = orderJpaMapper.toJpaEntity(order);
                orderJpaEntity.setCustomer(customerJpaEntity);
                orderJpaEntity.setUser(userJpaEntity);

                for (OrderItem domainItem : order.getItems()) {
                    OrderItemJpaEntity jpaItem = orderItemJpaMapper.toJpaEntity(domainItem);
                    OfferJpaEntity offerJpaEntity = offerRepositoryPort.findById(domainItem.getOffer().getId())
                            .map(offerJpaMapper::toJpaEntity)
                            .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem. Offer ID: " + domainItem.getOffer().getId()));
                    jpaItem.setOffer(offerJpaEntity);
                    orderJpaEntity.addOrderItem(jpaItem);
                }

            } else {
                orderJpaEntity = springDataOrderRepository.findById(order.getId())
                        .map(existingEntity -> {
                            orderJpaMapper.updateJpaEntityFromDomain(order, existingEntity);
                            existingEntity.setCustomer(customerJpaEntity);
                            existingEntity.setUser(userJpaEntity);

                            existingEntity.getItems().clear();
                            for (OrderItem domainItem : order.getItems()) {
                                OrderItemJpaEntity jpaItem = orderItemJpaMapper.toJpaEntity(domainItem);
                                OfferJpaEntity offerJpaEntity = offerRepositoryPort.findById(domainItem.getOffer().getId())
                                        .map(offerJpaMapper::toJpaEntity)
                                        .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem. Offer ID: " + domainItem.getOffer().getId()));
                                jpaItem.setOffer(offerJpaEntity);
                                existingEntity.addOrderItem(jpaItem);
                            }
                            return existingEntity;
                        })
                        .orElseThrow(() -> new DataPersistenceException("Order with ID '" + order.getId() + "' not found for update."));
            }

            OrderJpaEntity savedEntity = springDataOrderRepository.save(orderJpaEntity);

            Order resultOrder = orderJpaMapper.toDomainEntity(savedEntity);

            Customer domainCustomer = null;
            if (savedEntity.getCustomer() != null) {
                domainCustomer = customerRepositoryPort.findById(savedEntity.getCustomer().getId())
                        .orElseThrow(() -> new CustomerNotFoundException("Customer not found for order ID " + savedEntity.getId()));
            }

            User domainUser = null;
            if (savedEntity.getUser() != null) {
                domainUser = userRepositoryPort.findById(savedEntity.getUser().getId())
                        .orElseThrow(() -> new UserNotFoundException("User not found for order ID " + savedEntity.getId()));
            }

            List<OrderItem> domainItems = savedEntity.getItems().stream()
                    .map(orderItemJpaEntity -> {
                        OrderItem domainOrderItem = orderItemJpaMapper.toDomainEntity(orderItemJpaEntity);
                        Offer domainOffer = null;
                        if (orderItemJpaEntity.getOffer() != null) {
                            domainOffer = offerRepositoryPort.findById(orderItemJpaEntity.getOffer().getId())
                                    .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem ID " + orderItemJpaEntity.getId()));
                        }
                        return OrderItem.withId(
                                domainOrderItem.getId(),
                                domainOffer,
                                domainOrderItem.getQuantity(),
                                domainOrderItem.getPriceAtTimeOfOrder(),
                                domainOrderItem.getCreatedAt(),
                                domainOrderItem.getUpdatedAt()
                        );
                    })
                    .collect(Collectors.toList());

            return Order.withId(
                    resultOrder.getId(),
                    domainCustomer,
                    domainUser,
                    resultOrder.getStatus(),
                    domainItems,
                    resultOrder.getCreatedAt(),
                    resultOrder.getUpdatedAt()
            );
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save order due to data integrity violation. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to save order. " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findByIdWithDetails(id)
                .map(orderJpaEntity -> {
                    Order order = orderJpaMapper.toDomainEntity(orderJpaEntity);

                    Customer domainCustomer = null;
                    if (orderJpaEntity.getCustomer() != null) {
                        domainCustomer = customerRepositoryPort.findById(orderJpaEntity.getCustomer().getId())
                                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for order ID " + id));
                    }

                    User domainUser = null;
                    if (orderJpaEntity.getUser() != null) {
                        domainUser = userRepositoryPort.findById(orderJpaEntity.getUser().getId())
                                .orElseThrow(() -> new UserNotFoundException("User not found for order ID " + id));
                    }

                    List<OrderItem> domainItems = orderJpaEntity.getItems().stream()
                            .map(orderItemJpaEntity -> {
                                OrderItem domainOrderItem = orderItemJpaMapper.toDomainEntity(orderItemJpaEntity);
                                Offer domainOffer = null;
                                if (orderItemJpaEntity.getOffer() != null) {
                                    domainOffer = offerRepositoryPort.findById(orderItemJpaEntity.getOffer().getId())
                                            .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem ID " + orderItemJpaEntity.getId()));
                                }
                                return OrderItem.withId(
                                        domainOrderItem.getId(),
                                        domainOffer,
                                        domainOrderItem.getQuantity(),
                                        domainOrderItem.getPriceAtTimeOfOrder(),
                                        domainOrderItem.getCreatedAt(),
                                        domainOrderItem.getUpdatedAt()
                                );
                            })
                            .collect(Collectors.toList());

                    return Order.withId(
                            order.getId(),
                            domainCustomer,
                            domainUser,
                            order.getStatus(),
                            domainItems,
                            order.getCreatedAt(),
                            order.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        return springDataOrderRepository.findAllWithDetails(pageable)
                .map(orderJpaEntity -> {
                    Order order = orderJpaMapper.toDomainEntity(orderJpaEntity);

                    Customer domainCustomer = null;
                    if (orderJpaEntity.getCustomer() != null) {
                        domainCustomer = customerRepositoryPort.findById(orderJpaEntity.getCustomer().getId())
                                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for order ID " + orderJpaEntity.getId()));
                    }

                    User domainUser = null;
                    if (orderJpaEntity.getUser() != null) {
                        domainUser = userRepositoryPort.findById(orderJpaEntity.getUser().getId())
                                .orElseThrow(() -> new UserNotFoundException("User not found for order ID " + orderJpaEntity.getId()));
                    }

                    List<OrderItem> domainItems = orderJpaEntity.getItems().stream()
                            .map(orderItemJpaEntity -> {
                                OrderItem domainOrderItem = orderItemJpaMapper.toDomainEntity(orderItemJpaEntity);
                                Offer domainOffer = null;
                                if (orderItemJpaEntity.getOffer() != null) {
                                    domainOffer = offerRepositoryPort.findById(orderItemJpaEntity.getOffer().getId())
                                            .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem ID " + orderItemJpaEntity.getId()));
                                }
                                return OrderItem.withId(
                                        domainOrderItem.getId(),
                                        domainOffer,
                                        domainOrderItem.getQuantity(),
                                        domainOrderItem.getPriceAtTimeOfOrder(),
                                        domainOrderItem.getCreatedAt(),
                                        domainOrderItem.getUpdatedAt()
                                );
                            })
                            .collect(Collectors.toList());

                    return Order.withId(
                            order.getId(),
                            domainCustomer,
                            domainUser,
                            order.getStatus(),
                            domainItems,
                            order.getCreatedAt(),
                            order.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findByCustomerId(Long customerId, Pageable pageable) {
        return springDataOrderRepository.findByCustomerIdWithDetails(customerId, pageable)
                .map(orderJpaEntity -> {
                    Order order = orderJpaMapper.toDomainEntity(orderJpaEntity);

                    Customer domainCustomer = null;
                    if (orderJpaEntity.getCustomer() != null) {
                        domainCustomer = customerRepositoryPort.findById(orderJpaEntity.getCustomer().getId())
                                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for order ID " + orderJpaEntity.getId()));
                    }

                    User domainUser = null;
                    if (orderJpaEntity.getUser() != null) {
                        domainUser = userRepositoryPort.findById(orderJpaEntity.getUser().getId())
                                .orElseThrow(() -> new UserNotFoundException("User not found for order ID " + orderJpaEntity.getId()));
                    }

                    List<OrderItem> domainItems = orderJpaEntity.getItems().stream()
                            .map(orderItemJpaEntity -> {
                                OrderItem domainOrderItem = orderItemJpaMapper.toDomainEntity(orderItemJpaEntity);
                                Offer domainOffer = null;
                                if (orderItemJpaEntity.getOffer() != null) {
                                    domainOffer = offerRepositoryPort.findById(orderItemJpaEntity.getOffer().getId())
                                            .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem ID " + orderItemJpaEntity.getId()));
                                }
                                return OrderItem.withId(
                                        domainOrderItem.getId(),
                                        domainOffer,
                                        domainOrderItem.getQuantity(),
                                        domainOrderItem.getPriceAtTimeOfOrder(),
                                        domainOrderItem.getCreatedAt(),
                                        domainOrderItem.getUpdatedAt()
                                );
                            })
                            .collect(Collectors.toList());

                    return Order.withId(
                            order.getId(),
                            domainCustomer,
                            domainUser,
                            order.getStatus(),
                            domainItems,
                            order.getCreatedAt(),
                            order.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findByUserId(Long userId, Pageable pageable) {
        return springDataOrderRepository.findByUserIdWithDetails(userId, pageable)
                .map(orderJpaEntity -> {
                    Order order = orderJpaMapper.toDomainEntity(orderJpaEntity);

                    Customer domainCustomer = null;
                    if (orderJpaEntity.getCustomer() != null) {
                        domainCustomer = customerRepositoryPort.findById(orderJpaEntity.getCustomer().getId())
                                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for order ID " + orderJpaEntity.getId()));
                    }

                    User domainUser = null;
                    if (orderJpaEntity.getUser() != null) {
                        domainUser = userRepositoryPort.findById(orderJpaEntity.getUser().getId())
                                .orElseThrow(() -> new UserNotFoundException("User not found for order ID " + orderJpaEntity.getId()));
                    }

                    List<OrderItem> domainItems = orderJpaEntity.getItems().stream()
                            .map(orderItemJpaEntity -> {
                                OrderItem domainOrderItem = orderItemJpaMapper.toDomainEntity(orderItemJpaEntity);
                                Offer domainOffer = null;
                                if (orderItemJpaEntity.getOffer() != null) {
                                    domainOffer = offerRepositoryPort.findById(orderItemJpaEntity.getOffer().getId())
                                            .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem ID " + orderItemJpaEntity.getId()));
                                }
                                return OrderItem.withId(
                                        domainOrderItem.getId(),
                                        domainOffer,
                                        domainOrderItem.getQuantity(),
                                        domainOrderItem.getPriceAtTimeOfOrder(),
                                        domainOrderItem.getCreatedAt(),
                                        domainOrderItem.getUpdatedAt()
                                );
                            })
                            .collect(Collectors.toList());

                    return Order.withId(
                            order.getId(),
                            domainCustomer,
                            domainUser,
                            order.getStatus(),
                            domainItems,
                            order.getCreatedAt(),
                            order.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findByStatus(OrderStatus status, Pageable pageable) {
        return springDataOrderRepository.findByStatusWithDetails(status, pageable)
                .map(orderJpaEntity -> {
                    Order order = orderJpaMapper.toDomainEntity(orderJpaEntity);

                    Customer domainCustomer = null;
                    if (orderJpaEntity.getCustomer() != null) {
                        domainCustomer = customerRepositoryPort.findById(orderJpaEntity.getCustomer().getId())
                                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for order ID " + orderJpaEntity.getId()));
                    }

                    User domainUser = null;
                    if (orderJpaEntity.getUser() != null) {
                        domainUser = userRepositoryPort.findById(orderJpaEntity.getUser().getId())
                                .orElseThrow(() -> new UserNotFoundException("User not found for order ID " + orderJpaEntity.getId()));
                    }

                    List<OrderItem> domainItems = orderJpaEntity.getItems().stream()
                            .map(orderItemJpaEntity -> {
                                OrderItem domainOrderItem = orderItemJpaMapper.toDomainEntity(orderItemJpaEntity);
                                Offer domainOffer = null;
                                if (orderItemJpaEntity.getOffer() != null) {
                                    domainOffer = offerRepositoryPort.findById(orderItemJpaEntity.getOffer().getId())
                                            .orElseThrow(() -> new OfferNotFoundException("Offer not found for OrderItem ID " + orderItemJpaEntity.getId()));
                                }
                                return OrderItem.withId(
                                        domainOrderItem.getId(),
                                        domainOffer,
                                        domainOrderItem.getQuantity(),
                                        domainOrderItem.getPriceAtTimeOfOrder(),
                                        domainOrderItem.getCreatedAt(),
                                        domainOrderItem.getUpdatedAt()
                                );
                            })
                            .collect(Collectors.toList());

                    return Order.withId(
                            order.getId(),
                            domainCustomer,
                            domainUser,
                            order.getStatus(),
                            domainItems,
                            order.getCreatedAt(),
                            order.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            springDataOrderRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to delete order with ID '" + id + "' due to data access error. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to delete order with ID '" + id + "'. " + e.getMessage(), e);
        }
    }
}