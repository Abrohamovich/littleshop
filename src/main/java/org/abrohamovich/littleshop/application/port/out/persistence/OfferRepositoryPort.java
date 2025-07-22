package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OfferRepositoryPort {
    Offer save(Offer offer);

    Optional<Offer> findById(Long id);

    Optional<Offer> findByName(String name);

    Page<Offer> findAll(Pageable pageable);

    Page<Offer> findByNameLike(String name, Pageable pageable);

    Page<Offer> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Offer> findBySupplierId(Long supplierId, Pageable pageable);

    Page<Offer> findByPriceIsGreaterThanEqual(Double price, Pageable pageable);

    Page<Offer> findByPriceIsLessThanEqual(Double price, Pageable pageable);

    Page<Offer> findByPriceIsGreaterThanEqualAndPriceLessThanEqual(Double minPrice, Double maxPrice, Pageable pageable);

    void deleteById(Long id);
}
