package org.abrohamovich.littleshop.domain.exception.user;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class UserNotFoundException extends ModelNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
