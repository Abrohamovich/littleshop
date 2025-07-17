package org.abrohamovich.littleshop.application.port.in.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetOfferUseCase {
    OfferResponse findById(Long id);
    Page<OfferResponse> findAll(Pageable pageable);
    Page<OfferResponse> findByNameLike(String name, Pageable pageable);
    Page<OfferResponse> findByCategoryId(Long categoryId, Pageable pageable);
    Page<OfferResponse> findBySupplierId(Long supplierId, Pageable pageable);
    Page<OfferResponse> findByPriceIsGreaterThanEquals(Double price, Pageable pageable);
    Page<OfferResponse> findByPriceIsLessThanEquals(Double price, Pageable pageable);
    Page<OfferResponse> findByPriceIsGreaterThanEqualsAndLessThanEquals(Double price, Pageable pageable);
}
