package org.abrohamovich.littleshop.application.port.in.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferUpdateCommand;

public interface UpdateOfferUseCase {
    OfferResponse update(Long id, OfferUpdateCommand command);
}
