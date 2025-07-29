package org.abrohamovich.littleshop.domain.exception.category;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class CategoryNotFoundException extends ModelNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
