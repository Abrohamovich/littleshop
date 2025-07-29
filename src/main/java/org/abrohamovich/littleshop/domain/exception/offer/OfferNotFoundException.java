package org.abrohamovich.littleshop.domain.exception.offer;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class OfferNotFoundException extends ModelNotFoundException {
    public OfferNotFoundException(String message) {
        super(message);
    }

    public OfferNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
