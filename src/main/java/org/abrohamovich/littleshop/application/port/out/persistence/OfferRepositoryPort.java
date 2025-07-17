package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OfferRepositoryPort {
    Offer save(Offer offer);
    Optional<Offer> findOfferById(Long id);
    Page<Offer> findAllOffers(Pageable pageable);
    Page<Offer> findOffersByNameLike(String name, Pageable pageable);
    Page<Offer> findOffersByCategoryId(Long categoryId, Pageable pageable);
    Page<Offer> findOffersBySupplierId(Long supplierId, Pageable pageable);
    Page<Offer> findOffersByPriceIsGreaterThanEquals(Double price, Pageable pageable);
    Page<Offer> findOffersByPriceIsLessThanEquals(Double price, Pageable pageable);
    Page<Offer> findOffersByPriceIsGreaterThanEqualsAndLessThanEquals(Double price, Pageable pageable);
    void deleteById(Long id);
}
