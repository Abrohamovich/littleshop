package org.abrohamovich.littleshop.domain.exception.category;

public class CategoryValidationException extends RuntimeException {
    public CategoryValidationException(String message) {
        super(message);
    }
}
