package org.abrohamovich.littleshop.application.port.in.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferCreateCommand;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;

public interface CreateOfferUseCase {
    OfferResponse save(OfferCreateCommand command);
}
