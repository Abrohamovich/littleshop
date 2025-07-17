package org.abrohamovich.littleshop.application.port.in.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierUpdateCommand;

public interface UpdateSupplierUseCase {
    SupplierResponse updateSupplier(SupplierUpdateCommand command);
}
