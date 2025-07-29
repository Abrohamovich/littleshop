package org.abrohamovich.littleshop.domain.exception.orderItem;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class OrderItemNotFoundException extends ModelNotFoundException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }

    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}