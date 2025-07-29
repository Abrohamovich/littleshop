package org.abrohamovich.littleshop.domain.exception.order;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class OrderNotFoundException extends ModelNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
