package org.abrohamovich.littleshop.domain.exception.order;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class OrderValidationException extends ModelValidationException {
    public OrderValidationException(String message) {
        super(message);
    }

    public OrderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
