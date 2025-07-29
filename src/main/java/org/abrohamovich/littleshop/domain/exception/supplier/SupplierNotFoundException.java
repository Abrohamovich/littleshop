package org.abrohamovich.littleshop.domain.exception.supplier;

import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;

public class SupplierNotFoundException extends ModelNotFoundException {
    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
