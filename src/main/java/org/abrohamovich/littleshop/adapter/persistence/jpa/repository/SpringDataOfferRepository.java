package org.abrohamovich.littleshop.adapter.persistence.jpa.repository;

import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.OfferJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataOfferRepository extends JpaRepository<OfferJpaEntity, Long> {

    @Query("SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.id = :id")
    Optional<OfferJpaEntity> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.name = :name")
    Optional<OfferJpaEntity> findByNameWithDetails(@Param("name") String name);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o")
    Page<OfferJpaEntity> findAll(Pageable pageable);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<OfferJpaEntity> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.category.id = :categoryId",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o WHERE o.category.id = :categoryId")
    Page<OfferJpaEntity> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.supplier.id = :supplierId",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o WHERE o.supplier.id = :supplierId")
    Page<OfferJpaEntity> findBySupplierId(@Param("supplierId") Long supplierId, Pageable pageable);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.price >= :price",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o WHERE o.price >= :price")
    Page<OfferJpaEntity> findByPriceIsGreaterThanEqual(@Param("price") Double price, Pageable pageable);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.price <= :price",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o WHERE o.price <= :price")
    Page<OfferJpaEntity> findByPriceIsLessThanEqual(@Param("price") Double price, Pageable pageable);

    @Query(value = "SELECT o FROM OfferJpaEntity o LEFT JOIN FETCH o.category LEFT JOIN FETCH o.supplier WHERE o.price >= :min AND o.price <= :max",
            countQuery = "SELECT count(o) FROM OfferJpaEntity o WHERE o.price >= :min AND o.price <= :max")
    Page<OfferJpaEntity> findByPriceIsGreaterThanEqualAndPriceLessThanEqual(@Param("min") Double min, @Param("max") Double max, Pageable pageable);
}