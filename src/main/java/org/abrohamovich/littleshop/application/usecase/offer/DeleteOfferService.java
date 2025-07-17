package org.abrohamovich.littleshop.application.usecase.offer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.offer.DeleteOfferUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;

@RequiredArgsConstructor
public class DeleteOfferService implements DeleteOfferUseCase {
    private final OfferRepositoryPort offerRepositoryPort;

    @Override
    public void deleteById(Long id) {
        if (offerRepositoryPort.findById(id).isEmpty()) {
            throw new OfferNotFoundException("Offer with ID '" + id + "' not found for deletion.");
        }

        offerRepositoryPort.deleteById(id);
    }
}
