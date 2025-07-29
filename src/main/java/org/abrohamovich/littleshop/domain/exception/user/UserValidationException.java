package org.abrohamovich.littleshop.domain.exception.user;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class UserValidationException extends ModelValidationException {
    public UserValidationException(String message) {
        super(message);
    }

    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
