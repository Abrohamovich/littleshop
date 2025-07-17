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
    Page<OfferResponse> getOffersByPriceIsGreaterThanEquals(Double price, Pageable pageable);
    Page<OfferResponse> getOffersByPriceIsLessThanEquals(Double price, Pageable pageable);
    Page<OfferResponse> getOffersByPriceIsGreaterThanEqualsAndLessThanEquals(Double price, Pageable pageable);
}
