package org.abrohamovich.littleshop.adapter.web.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierCreateCommand;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierUpdateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierWebMapper {
    SupplierCreateCommand toCreateCommand(SupplierCreateWebRequest request);

    SupplierUpdateCommand toUpdateCommand(SupplierUpdateWebRequest request);

    SupplierWebResponse toWebResponse(SupplierResponse response);
}
