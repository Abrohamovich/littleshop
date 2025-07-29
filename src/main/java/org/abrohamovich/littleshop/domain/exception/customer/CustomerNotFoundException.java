package org.abrohamovich.littleshop.domain.exception.customer;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class CustomerNotFoundException extends ModelNotFoundException {
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
