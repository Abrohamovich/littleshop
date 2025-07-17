package org.abrohamovich.littleshop.domain.exception.customer;

public class CustomerValidationException extends RuntimeException {
    public CustomerValidationException(String message) {
        super(message);
    }
}
