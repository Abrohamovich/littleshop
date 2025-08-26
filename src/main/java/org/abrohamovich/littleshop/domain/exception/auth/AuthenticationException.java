package org.abrohamovich.littleshop.domain.exception.auth;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class AuthenticationException extends ModelValidationException {
    public AuthenticationException(String message) {
        super(message);
    }
}
