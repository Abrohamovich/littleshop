package org.abrohamovich.littleshop.domain.exception.category;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class CategoryValidationException extends ModelValidationException {
    public CategoryValidationException(String message) {
        super(message);
    }

    public CategoryValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
