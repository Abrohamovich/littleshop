package org.abrohamovich.littleshop.domain.exception;

public class DataPersistenceException extends RuntimeException {
    public DataPersistenceException(String message, Exception e) {
        super(message);
    }
}
