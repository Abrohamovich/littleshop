package org.abrohamovich.littleshop.domain.exception.user;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
