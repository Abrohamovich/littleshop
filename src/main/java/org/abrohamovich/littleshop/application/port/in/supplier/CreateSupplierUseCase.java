package org.abrohamovich.littleshop.application.port.in.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierCreateCommand;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;

public interface CreateSupplierUseCase {
    SupplierResponse save(SupplierCreateCommand command);
}
