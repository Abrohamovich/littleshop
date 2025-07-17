package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OfferRepositoryPort {
    Offer save(Offer offer);
    Optional<Offer> findOfferById(Long id);
    Page<Offer> findAll(Pageable pageable);
    Page<Offer> findByNameLike(String name, Pageable pageable);
    Page<Offer> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Offer> findBySupplierId(Long supplierId, Pageable pageable);
    Page<Offer> findByPriceIsGreaterThanEquals(Double price, Pageable pageable);
    Page<Offer> findByPriceIsLessThanEquals(Double price, Pageable pageable);
    Page<Offer> findByPriceIsGreaterThanEqualsAndLessThanEquals(Double price, Pageable pageable);
    void deleteById(Long id);
}
