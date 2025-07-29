package org.abrohamovich.littleshop.domain.exception.customer;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class CustomerValidationException extends ModelValidationException {
    public CustomerValidationException(String message) {
        super(message);
    }

    public CustomerValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
