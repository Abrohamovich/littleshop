package org.abrohamovich.littleshop.domain.exception.offer;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class OfferValidationException extends ModelValidationException {
    public OfferValidationException(String message) {
        super(message);
    }

    public OfferValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
