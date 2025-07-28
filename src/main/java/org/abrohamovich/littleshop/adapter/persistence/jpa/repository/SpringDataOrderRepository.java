package org.abrohamovich.littleshop.adapter.persistence.jpa.repository;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.OrderJpaEntity;
import org.abrohamovich.littleshop.domain.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Импортируйте Query
import org.springframework.data.repository.query.Param; // Импортируйте Param

import java.util.Optional;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity,Long> {

    @Query("SELECT o FROM OrderJpaEntity o " +
            "LEFT JOIN FETCH o.customer c " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.offer " +
            "WHERE o.id = :id")
    Optional<OrderJpaEntity> findByIdWithDetails(@Param("id") Long id);

    @Query(value = "SELECT DISTINCT o FROM OrderJpaEntity o " +
            "LEFT JOIN FETCH o.customer c " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.offer",
            countQuery = "SELECT count(o) FROM OrderJpaEntity o")
    Page<OrderJpaEntity> findAllWithDetails(Pageable pageable);

    @Query(value = "SELECT DISTINCT o FROM OrderJpaEntity o " +
            "LEFT JOIN FETCH o.customer c " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.offer " +
            "WHERE c.id = :customerId",
            countQuery = "SELECT count(o) FROM OrderJpaEntity o WHERE o.customer.id = :customerId")
    Page<OrderJpaEntity> findByCustomerIdWithDetails(@Param("customerId") Long customerId, Pageable pageable);

    @Query(value = "SELECT DISTINCT o FROM OrderJpaEntity o " +
            "LEFT JOIN FETCH o.customer c " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.offer " +
            "WHERE u.id = :userId",
            countQuery = "SELECT count(o) FROM OrderJpaEntity o WHERE o.user.id = :userId")
    Page<OrderJpaEntity> findByUserIdWithDetails(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT DISTINCT o FROM OrderJpaEntity o " +
            "LEFT JOIN FETCH o.customer c " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.offer " +
            "WHERE o.status = :status",
            countQuery = "SELECT count(o) FROM OrderJpaEntity o WHERE o.status = :status")
    Page<OrderJpaEntity> findByStatusWithDetails(@Param("status") OrderStatus status, Pageable pageable);

    Page<OrderJpaEntity> findByCustomerId(Long customerId, Pageable pageable);
    Page<OrderJpaEntity> findByUserId(Long userId, Pageable pageable);
    Page<OrderJpaEntity> findByStatus(OrderStatus status, Pageable pageable);
}