package org.abrohamovich.littleshop.domain.exception.supplier;

import org.abrohamovich.littleshop.domain.exception.ModelValidationException;

public class SupplierValidationException extends ModelValidationException {
    public SupplierValidationException(String message) {
        super(message);
    }

    public SupplierValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
