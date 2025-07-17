package org.abrohamovich.littleshop.application.port.in.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetOfferUseCase {
    OfferResponse getOfferById(Long id);
    Page<OfferResponse> getAllOffers(Pageable pageable);
    Page<OfferResponse> getOffersByNameLike(String name, Pageable pageable);
    Page<OfferResponse> getOffersByCategoryId(Long categoryId, Pageable pageable);
    Page<OfferResponse> getOffersBySupplierId(Long supplierId, Pageable pageable);
    Page<OfferResponse> findOffersByPriceIsGreaterThanEquals(Double price, Pageable pageable);
    Page<OfferResponse> findOffersByPriceIsLessThanEquals(Double price, Pageable pageable);
    Page<OfferResponse> findOffersByPriceIsGreaterThanEqualsAndLessThanEquals(Double price, Pageable pageable);
}
