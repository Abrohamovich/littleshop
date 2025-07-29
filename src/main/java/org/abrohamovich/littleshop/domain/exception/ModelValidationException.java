package org.abrohamovich.littleshop.domain.exception;

public class ModelValidationException extends RuntimeException {
    public ModelValidationException(String message) {
        super(message);
    }

    public ModelValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
