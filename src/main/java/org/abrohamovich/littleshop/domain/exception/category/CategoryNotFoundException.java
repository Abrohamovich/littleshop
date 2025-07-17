package org.abrohamovich.littleshop.domain.exception.category;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
