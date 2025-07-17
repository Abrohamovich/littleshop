package org.abrohamovich.littleshop.application.usecase.offer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.port.in.offer.GetOfferUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GetOfferService implements GetOfferUseCase {
    private final OfferRepositoryPort offerRepositoryPort;

    @Override
    public OfferResponse findById(Long id) {
        return offerRepositoryPort.findById(id)
                .map(OfferResponse::toResponse)
                .orElseThrow(() -> new OfferNotFoundException("Offer with ID '" + id + "' not found."));
    }

    @Override
    public Page<OfferResponse> findAll(Pageable pageable) {
        return offerRepositoryPort.findAll(pageable)
                .map(OfferResponse::toResponse);
    }

    @Override
    public Page<OfferResponse> findByNameLike(String name, Pageable pageable) {
        return offerRepositoryPort.findByNameLike(name, pageable)
                .map(OfferResponse::toResponse);
    }

    @Override
    public Page<OfferResponse> findByCategoryId(Long categoryId, Pageable pageable) {
        return offerRepositoryPort.findByCategoryId(categoryId, pageable)
                .map(OfferResponse::toResponse);
    }

    @Override
    public Page<OfferResponse> findBySupplierId(Long supplierId, Pageable pageable) {
        return offerRepositoryPort.findBySupplierId(supplierId, pageable)
                .map(OfferResponse::toResponse);
    }

    @Override
    public Page<OfferResponse> findByPriceIsGreaterThanEquals(Double price, Pageable pageable) {
        return offerRepositoryPort.findByPriceIsGreaterThanEquals(price, pageable)
                .map(OfferResponse::toResponse);
    }

    @Override
    public Page<OfferResponse> findByPriceIsLessThanEquals(Double price, Pageable pageable) {
        return offerRepositoryPort.findByPriceIsLessThanEquals(price, pageable)
                .map(OfferResponse::toResponse);
    }

    @Override
    public Page<OfferResponse> findByPriceIsGreaterThanEqualsAndLessThanEquals(Double price, Pageable pageable) {
        return offerRepositoryPort.findByPriceIsGreaterThanEqualsAndLessThanEquals(price, pageable)
                .map(OfferResponse::toResponse);
    }
}
