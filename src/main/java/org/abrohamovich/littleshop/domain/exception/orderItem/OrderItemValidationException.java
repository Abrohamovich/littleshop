package org.abrohamovich.littleshop.domain.exception.orderItem;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class OrderItemValidationException extends ModelValidationException {
    public OrderItemValidationException(String message) {
        super(message);
    }

    public OrderItemValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
